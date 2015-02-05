package com.mentoracademy.servicesandbroadcasts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method to start the service
    public void startService(View view) {
        startService(new Intent(this, CustomService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(this, CustomService.class));
    }

    public void broadcastIntent(View view)
    {
        Intent intent = new Intent();
        intent.setAction("com.mentoracademy.CUSTOM_INTENT");
        sendBroadcast(intent);
    }
}
