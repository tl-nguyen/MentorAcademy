package com.softacad.androidcourse.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Stefan on 2.10.2014 г..
 */

public class InitialActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(Constants.TAG, "FragmentLayout: OnCreate()");
		// If portrait mode  - res/layout/fragment_layout.xml
		// If landscape mode - res/layout-land/fragment_layout.xml
		setContentView(R.layout.fragment_layout);
	}
}
