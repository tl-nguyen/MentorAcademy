package com.mentoracademy.servicesandbroadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by Stefan on 31.1.2015 г..
 */
public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null) {
            if(info.isConnected()) {

                Log.d("BROADCAST_RECEIVER", "Intent Detected!");
                Intent updateServiceIntent = new Intent(context, CustomIntentService.class);
                context.startService(updateServiceIntent);

                // e.g. To check the Network Name or other info:
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();
            }
        }
    }
}