package com.tlnguyen.fragmentspratices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.tlnguyen.fragmentspratices.multifragmentdemo.MultiFragmentDemoActivity;
import com.tlnguyen.fragmentspratices.simplefragmentdemo.SimpleFragmentDemoActivity;
import com.tlnguyen.fragmentspratices.viewpagerdemo.ViewPagerDemoActivity;


public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void simpleFragment(View v) {
        Intent simpleFragmentIntent = new Intent(this, SimpleFragmentDemoActivity.class);
        startActivity(simpleFragmentIntent);
    }

    public void multipleFragment(View v) {
        Intent multiFragmentIntent = new Intent(this, MultiFragmentDemoActivity.class);
        startActivity(multiFragmentIntent);
    }

    public void viewPager(View v) {
        Intent viewPagerIntent = new Intent(this, ViewPagerDemoActivity.class);
        startActivity(viewPagerIntent);
    }
}
