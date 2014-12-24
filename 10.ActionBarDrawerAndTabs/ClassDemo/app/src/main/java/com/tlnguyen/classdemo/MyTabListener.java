package com.tlnguyen.classdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

/**
 * Created by TL on 12/22/2014.
 */
public class MyTabListener implements ActionBar.TabListener {

    private Fragment fr;

    public MyTabListener(android.support.v4.app.Fragment fragmentOne) {
        fr = fragmentOne;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.frtest, fr);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
