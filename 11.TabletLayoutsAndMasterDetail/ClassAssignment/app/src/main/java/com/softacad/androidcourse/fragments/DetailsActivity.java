package com.softacad.androidcourse.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Stefan on 2.10.2014 г..
 */

// Secondary activity, shows what the user has selected something and screen is not large enough to show it all
public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Constants.TAG, "DetailsActivity");

        // If the screen changes to landscape mode at any moment, we switch back to multiPane view mode.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {

            DetailsFragment details = new DetailsFragment();
            // Get and set the position input by user which is the construction arguments for this fragment
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}
