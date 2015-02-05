package com.tlnguyen.classassignment.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.tlnguyen.classassignment.services.WeatherUpdateService;

public class WifiStatusReceiver extends BroadcastReceiver {
    public WifiStatusReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("WeatherWIFI", "wif intent Detected!");

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null) {
            if(info.isConnected()) {
                Log.d("WeatherWIFI", "Wifi on Detected!");
                Intent updateServiceIntent = new Intent(context, WeatherUpdateService.class);
                context.startService(updateServiceIntent);
            }
        }
    }
}
