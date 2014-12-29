package com.tlnguyen.cinemaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.commons.Constants;

public class MovieDetailActivity extends ActionBarActivity {

    private String mCinemaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initData();
        initViews();
    }

    private void initData() {
        mCinemaId = getIntent().getStringExtra(Constants.CINEMA_ID);
    }

    private void initViews() {
        // Set the up button to Actionbar programmatically
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
