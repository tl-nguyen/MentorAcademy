package com.tlnguyen.classdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    Fragment fragmentOne = new FragmentOne();
    Fragment fragmentTwo = new FragmentTwo();
    ActionBar.Tab tabOne;
    ActionBar.Tab tabTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tabOne = actionBar.newTab();
        tabOne.setIcon(android.R.drawable.btn_star);
        tabOne.setTabListener( new MyTabListener(fragmentOne));

        tabTwo = actionBar.newTab();
        tabTwo.setIcon(android.R.drawable.btn_minus);
        tabTwo.setTabListener( new MyTabListener(fragmentTwo));

        actionBar.addTab(tabOne);
        actionBar.addTab(tabTwo);
    }
}
