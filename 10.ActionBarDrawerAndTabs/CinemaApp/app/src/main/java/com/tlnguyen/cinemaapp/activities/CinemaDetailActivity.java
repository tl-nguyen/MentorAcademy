package com.tlnguyen.cinemaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.commons.Constants;
import com.tlnguyen.cinemaapp.helpers.ImageResizer;
import com.tlnguyen.cinemaapp.models.Cinema;

public class CinemaDetailActivity extends ActionBarActivity implements View.OnClickListener {

    private Cinema mCinema;

    private ImageView ivPhoto1;
    private ImageView ivPhoto2;
    private TextView tvAddress;
    private TextView tvWorkingTime;
    private Button btnMoviesAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);

        initData();
        initView();
    }

    private void initData() {
        String cinemaId = getIntent().getStringExtra(Constants.CINEMA_ID);

        if (cinemaId != null) {
            ParseQuery<Cinema> query = new ParseQuery<Cinema>(Constants.CINEMA_CLASS_NAME);
            query.whereEqualTo(Constants.ID_FIELD_NAME, cinemaId);
            try {
                mCinema = query.find().get(0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        ivPhoto1 = (ImageView) findViewById(R.id.ivPhoto1);
        ivPhoto2 = (ImageView) findViewById(R.id.ivPhoto2);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvWorkingTime = (TextView) findViewById(R.id.tvWorkingTime);
        btnMoviesAvailable = (Button) findViewById(R.id.btnMoviesAvailable);

        btnMoviesAvailable.setOnClickListener(this);

        // Set Page Title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mCinema.getTitle());
        }

        // Set Photos
        mCinema.getPhotos().get(1).getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    ivPhoto1.setImageBitmap(ImageResizer.decodeSampledBitmapFromByteArray(data, 100, 100));
                }
            }
        });

        mCinema.getPhotos().get(2).getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    ivPhoto2.setImageBitmap(ImageResizer.decodeSampledBitmapFromByteArray(data, 100, 100));
                }
            }
        });

        // Set Address and Working Time
        tvAddress.setText(mCinema.getAddress());
        tvWorkingTime.setText(mCinema.getWorkingTime());
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
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnMoviesAvailable:
                goToMoviesAvailableList();
                break;
        }
    }

    private void goToMoviesAvailableList() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.putExtra(Constants.TO_MOVIES_TAB, true);
        homeIntent.putExtra(Constants.CINEMA_ID, mCinema.getObjectId());
        startActivity(homeIntent);
    }
}
