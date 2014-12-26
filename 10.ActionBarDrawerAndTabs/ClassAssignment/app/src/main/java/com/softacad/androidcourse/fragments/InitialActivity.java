package com.softacad.androidcourse.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.softacad.androidcourse.fragments.fragments.ContactFragment;
import com.softacad.androidcourse.fragments.fragments.PlayFragment;
import com.softacad.androidcourse.fragments.fragments.TitlesFragment;

/**
 * Created by Stefan on 2.10.2014 г..
 */

public class InitialActivity extends Activity {

    Fragment posterFragment = new TitlesFragment();
    Fragment playFragment = new PlayFragment();
    Fragment contactFragment = new ContactFragment();

    ActionBar.Tab posterTab;
    ActionBar.Tab playTab;
    ActionBar.Tab contactTab;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(Constants.TAG, "FragmentLayout: OnCreate()");
		// If portrait mode  - res/layout/fragment_layout.xml
		// If landscape mode - res/layout-land/fragment_layout.xml
		setContentView(R.layout.init_layout);

        ActionBar actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        posterTab = actionBar.newTab();
        posterTab.setIcon(android.R.drawable.btn_star);
        posterTab.setTabListener( new MyTabListener(posterFragment));

        playTab = actionBar.newTab();
        playTab.setIcon(android.R.drawable.btn_minus);
        playTab.setTabListener( new MyTabListener(playFragment));

        contactTab = actionBar.newTab();
        contactTab.setIcon(android.R.drawable.btn_plus);
        contactTab.setTabListener( new MyTabListener(contactFragment));

        actionBar.addTab(posterTab);
        actionBar.addTab(playTab);
        actionBar.addTab(contactTab);
    }


}
