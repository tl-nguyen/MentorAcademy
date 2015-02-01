package com.tlnguyen.handlinglocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

/**
 * Use the Broadcast receiver to monitor the state changes of the providers
 */
public class NetworkProviderStatusReceiver extends BroadcastReceiver {

    private final String LOG_TAG = "Monitor Location";

    public NetworkProviderStatusReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();

        Log.d(LOG_TAG, "Monitor Location - Broadcast Receiver Action: " + action);

        if (action.equalsIgnoreCase(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            boolean state = extras.getBoolean("state");
            Log.d(LOG_TAG, "Monitor Location - Airplane Mode changed to " + (state ? "ON" : "OFF"));
        }
        else if (action.equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            Log.d(LOG_TAG, "Monitor Location - Wifi Radio Available: " + (wifiInfo.isAvailable() ? "ON" : "OFF"));
        }
    }

    // Programmatically put the intent filters, not in the manifest
    public void start(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, filter);
    }

    public void stop (Context context) {
        context.unregisterReceiver(this);
    }
}
