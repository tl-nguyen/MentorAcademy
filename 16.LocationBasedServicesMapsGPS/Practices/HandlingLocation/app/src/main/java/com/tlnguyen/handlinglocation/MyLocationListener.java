package com.tlnguyen.handlinglocation;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by tl on 30.01.15.
 */
public class MyLocationListener implements LocationListener {

    private final String LOG_TAG = "Monitor Location";

    @Override
    public void onLocationChanged(Location location) {
        Log.d(LOG_TAG, "Monitor Location - onLocationChanged");

        String provider = location.getProvider();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        float accuracy = location.getAccuracy();
        long time = location.getTime();

        Log.d(LOG_TAG, "Monitor Location - onLocationChanged: provider: "
                + provider
                + ". Long/lat: "
                + longitude + "/" + latitude
                + ". Accuracy: " + accuracy
                + ". Time: " + time);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(LOG_TAG, "Monitor Location - Status Changed " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(LOG_TAG, "Monitor Location - Provider Enabled " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(LOG_TAG, "Monitor Location - Provider Disabled " + provider);
    }
}
