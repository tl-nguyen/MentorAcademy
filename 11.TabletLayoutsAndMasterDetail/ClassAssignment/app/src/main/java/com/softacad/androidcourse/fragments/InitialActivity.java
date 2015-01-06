package com.softacad.androidcourse.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by Stefan on 2.10.2014 г..
 */

public class InitialActivity extends Activity {

    private boolean mTablet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

    Log.d(Constants.TAG, "FragmentLayout: OnCreate()");
		// If portrait mode  - res/layout/fragment_layout.xml
		// If landscape mode - res/layout-land/fragment_layout.xml

        if (isTabletScreen()) {
            setContentView(R.layout.tablet_layout);
        }
        else {
            setContentView(R.layout.fragment_layout);
        }
	}

    private boolean isTabletScreen() {
        // Check that the screen is tablet's screen
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float widthDpi = metrics.xdpi;
        float heightDpi = metrics.ydpi;
        float widthInches = widthPixels / widthDpi;
        float heightInches = heightPixels / heightDpi;

        double diagonalInches = Math.sqrt((widthInches * widthInches) + (heightInches * heightInches));

        return diagonalInches >= 7;
    }
}
