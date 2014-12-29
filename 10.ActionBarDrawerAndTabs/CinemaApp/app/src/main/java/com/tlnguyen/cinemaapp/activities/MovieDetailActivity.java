package com.tlnguyen.cinemaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.commons.Constants;
import com.tlnguyen.cinemaapp.helpers.ImageResizer;
import com.tlnguyen.cinemaapp.models.Movie;

public class MovieDetailActivity extends ActionBarActivity {

    private Movie mMovie;
    private String mCinemaId;

    private ImageView mIvPhoto;
    private TextView mTvCast;
    private ListView mLvMovieCinema;



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

        if (movieId != null) {
            ParseQuery<Movie> query = new ParseQuery<Movie>(Constants.MOVIE_CLASS_NAME);
            query.whereEqualTo(Constants.ID_FIELD_NAME, movieId);
            try {
                mMovie = query.find().get(0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void initViews() {
        mIvPhoto = (ImageView) findViewById(R.id.ivPhoto);
        mTvCast = (TextView) findViewById(R.id.tvCast);
        mLvMovieCinema = (ListView) findViewById(R.id.lvMovieCinema);

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
}
