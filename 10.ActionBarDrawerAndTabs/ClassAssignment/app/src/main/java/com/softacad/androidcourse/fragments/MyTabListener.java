package com.softacad.androidcourse.fragments;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by TL on 12/22/2014.
 */
public class MyTabListener implements ActionBar.TabListener {

    private Fragment fr;

    public MyTabListener(Fragment fragmentOne) {
        fr = fragmentOne;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.fl_main, fr);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
