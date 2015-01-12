package com.tlnguyen.classdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class SecondActivity extends ActionBarActivity {

    private TextView mTvNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mTvNotification = (TextView) findViewById(R.id.tvNotification);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        String notification = bundle.getString("NOTIFICATION");

        if (notification != null) {
            mTvNotification.setText(notification);
        }
    }
}
