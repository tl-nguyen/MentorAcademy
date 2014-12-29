package com.tlnguyen.cinemaapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.adapters.MovieCinemaAdapter;
import com.tlnguyen.cinemaapp.commons.Constants;
import com.tlnguyen.cinemaapp.helpers.ImageResizer;
import com.tlnguyen.cinemaapp.models.Cinema;
import com.tlnguyen.cinemaapp.models.Movie;
import com.tlnguyen.cinemaapp.models.MovieCinema;
import com.tlnguyen.cinemaapp.models.Order;

import java.util.List;

public class MovieDetailActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private Movie mMovie;
    private String mCinemaId;
    private List<MovieCinema> mMovieCinemas;

    private ImageView mIvPhoto;
    private TextView mTvCast;
    private ListView mLvMovieCinema;
    private EditText mEtQuantity;

    private MovieCinemaAdapter mMovieCinemaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initData();
        initViews();
    }

    private void initData() {
        // If the previous screen was from the movies of a specific cinema
        mCinemaId = getIntent().getStringExtra(Constants.CINEMA_ID);

        String movieId = getIntent().getStringExtra(Constants.MOVIE_ID);

        // Request Movie Object
        if (movieId != null) {
            ParseQuery<Movie> movieQuery = new ParseQuery<Movie>(Constants.MOVIE_CLASS_NAME);
            movieQuery.whereEqualTo(Constants.ID_FIELD_NAME, movieId);
            try {
                mMovie = movieQuery.find().get(0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Request MovieCinema objects
        ParseQuery<MovieCinema> movieCinemasQuery = new ParseQuery<MovieCinema>(Constants.MOVIECINEMA_CLASS_NAME);
        movieCinemasQuery.whereEqualTo(Constants.MOVIECINEMA_MOVIE_FIELD, mMovie);

        try {
            mMovieCinemas = movieCinemasQuery.find();
            mMovieCinemaAdapter = new MovieCinemaAdapter(this, mMovieCinemas);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        mIvPhoto = (ImageView) findViewById(R.id.ivPhoto);
        mTvCast = (TextView) findViewById(R.id.tvCast);
        mLvMovieCinema = (ListView) findViewById(R.id.lvMovieCinemas);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Set the up button to Actionbar programmatically
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Set Page Title
            actionBar.setTitle(mMovie.getTitle());
        }

        // Set Movie Photo
        mMovie.getPicture().getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    mIvPhoto.setImageBitmap(ImageResizer.decodeSampledBitmapFromByteArray(data, 100, 100));
                }
            }
        });

        // Set Movie Cast
        mTvCast.setText(mMovie.getCast());

        // Set adapter to Cinemas list view
        mLvMovieCinema.setAdapter(mMovieCinemaAdapter);
        mLvMovieCinema.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                goToMovies();
                break;
            case R.id.action_logout:
                logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToMovies() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.putExtra(Constants.TO_MOVIES_TAB, true);
        if (mCinemaId != null) {
            homeIntent.putExtra(Constants.CINEMA_ID, mCinemaId);
        }
        startActivity(homeIntent);
    }

    private void logout() {
        ParseUser.logOut();
        goToLoginScreen();
    }

    private void goToLoginScreen() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mMovieCinemas.get(position).getAvailableSeats() > 0) {
            // When there're still free seats
            showOrderDialog(position);
        }
        else {
            // When there's no seats available
            Toast.makeText(this, getString(R.string.no_more_tickets), Toast.LENGTH_SHORT).show();
        }
    }

    private void showOrderDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View orderDialogView = inflater.inflate(R.layout.order_dialog, null);

        mEtQuantity = (EditText) orderDialogView.findViewById(R.id.etQuantity);

        builder.setView(orderDialogView)
                .setTitle(getString(R.string.tickets_quantity_order))
                .setPositiveButton(getString(R.string.reserve), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Cinema selectedCinema = mMovieCinemas.get(position).getCinema();
                        ParseUser currentUser = ParseUser.getCurrentUser();

                        int availableQuantity = mMovieCinemas.get(position).getAvailableSeats();
                        int orderedQuantity = Integer.parseInt(mEtQuantity.getText().toString());

                        if (orderedQuantity > availableQuantity) {
                            // When your order exceeds the available seats
                            Toast.makeText(MovieDetailActivity.this, String.format(getString(R.string.invalid_order_warning), availableQuantity), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // Make new order and save it
                            makeOrder(selectedCinema, currentUser, availableQuantity, orderedQuantity, position);
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void makeOrder(Cinema selectedCinema, ParseUser currentUser, int availableQuantity, int orderedQuantity, int position) {
        Order order = new Order();
        order.setMovie(mMovie);
        order.setCinema(selectedCinema);
        order.setCreatedBy(currentUser);
        order.setQuantity(orderedQuantity);

        mMovieCinemas.get(position).setAvailableSeats(availableQuantity - orderedQuantity);
        mMovieCinemaAdapter.notifyDataSetChanged();

        try {
            order.save();
            mMovieCinemas.get(position).save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, String.format(getString(R.string.success_order_message),
                currentUser.getUsername(),
                orderedQuantity,
                mMovie.getTitle(),
                selectedCinema.getTitle()), Toast.LENGTH_LONG).show();
    }
}
