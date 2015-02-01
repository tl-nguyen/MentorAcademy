package com.tlnguyen.handlinglocation.activities;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.tlnguyen.handlinglocation.MyLocationListener;
import com.tlnguyen.handlinglocation.NetworkProviderStatusReceiver;
import com.tlnguyen.handlinglocation.R;

import java.util.List;


public class LocationActivity extends ActionBarActivity {

    private final String LOG_TAG = "Monitor Location";

    private LocationListener mNetworkListener;
    private LocationListener mGpsListener;
    private LocationListener mPassiveListener;

    private NetworkProviderStatusReceiver mStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAccurateProvider(MenuItem item) {
        Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> matchingProviderNames = lm.getProviders(criteria, false);

        for (String providerName: matchingProviderNames) {
            LocationProvider provider = lm.getProvider(providerName);

            logLocationProvider(provider);
        }
    }

    private void logLocationProvider(LocationProvider provider) {
        String name = provider.getName();
        int horizontalAccuracy = provider.getAccuracy();
        int powerRequirements = provider.getPowerRequirement();
        boolean hasMonetaryCost = provider.hasMonetaryCost();
        boolean requiresCell = provider.requiresCell();
        boolean requiresNetwork = provider.requiresNetwork();
        boolean requiresSatellite = provider.requiresSatellite();
        boolean supportAltitude = provider.supportsAltitude();
        boolean supportsBearing = provider.supportsBearing();
        boolean supportsSpeed = provider.supportsSpeed();

        Log.d(LOG_TAG, "Monitor Location - Location Provider: "
                + " Name:" + name
                + " | horizontalAccuracy:" + horizontalAccuracy
                + " | powerRequirements:" + powerRequirements
                + " | hasMonetaryCost:" + hasMonetaryCost
                + " | requiresCell:" + requiresCell
                + " | requiresNetwork:" + requiresNetwork
                + " | requiresSatellite:" + requiresSatellite
                + " | supportAltitude:" + supportAltitude
                + " | supportsBearing:" + supportsBearing
                + " | supportsSpeed:" + supportsSpeed);
    }

    public void onLowePowerProvider(MenuItem item) {
        Criteria criteria = new Criteria();

        criteria.setPowerRequirement(Criteria.POWER_LOW);

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> matchingProviderNames = lm.getProviders(criteria, false);

        for (String providerName: matchingProviderNames) {
            LocationProvider provider = lm.getProvider(providerName);

            logLocationProvider(provider);
        }
    }

    public void onStartNetworkListener(MenuItem item) {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (confirmNetworkProviderAvailable(lm)) {
            // Start the broadcast receiver to listen to network provider status changes
            mStatusReceiver = new NetworkProviderStatusReceiver();
            mStatusReceiver.start(this);

            mNetworkListener = new MyLocationListener();
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, mNetworkListener);
        }

        mGpsListener = new MyLocationListener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mGpsListener);
    }

    // Monitor location without activating hardware, receive notifications when another source requests it
    public void onStartPassiveListener(MenuItem item) {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        mPassiveListener = new MyLocationListener();
        lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, mPassiveListener);
    }

    // Check for network provider if it's enabled or not
    private boolean confirmNetworkProviderAvailable(LocationManager lm) {
        return confirmNetworkProviderEnabled(lm) &&
                confirmAirPlaneModeIsOff(lm) &&
                confirmWifiAvailable();
    }

    private boolean confirmNetworkProviderEnabled(LocationManager lm) {
        boolean isAvailable = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isAvailable) {
            Log.d(LOG_TAG, "Monitor Location - NetworkProvider is not available, please enable location services");

            // Go to system settings to change it
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        return isAvailable;
    }

    private boolean confirmAirPlaneModeIsOff(LocationManager lm) {
        boolean isOff = Settings.System.getInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 0;

        if (!isOff) {
            Log.d(LOG_TAG, "Monitor Location - AirPlaneMode Is on, please disable it");

            // Go to system settings to change it
            startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
        }

        return isOff;
    }

    private boolean confirmWifiAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isAvailable = wifiInfo.isAvailable();

        if (!isAvailable) {
            Log.d(LOG_TAG, "Monitor Location - Wifi is not available, please turn on wifi");

            // Go to system settings to change it
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }

        return isAvailable;
    }

    public void onStopNetworkListener(MenuItem item) {
        Log.d(LOG_TAG, "Monitor Location - Stop Listening");
        doStopListening();
    }

    public void onRecentLocation(MenuItem item) {
        Log.d(LOG_TAG, "Monitor Location - Recent Location");

        Location networkLocation;
        Location gpsLocation;

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        networkLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        logLocation(networkLocation);
        logLocation(gpsLocation);
    }

    private void logLocation(Location location) {
        if (location != null) {
            String provider = location.getProvider();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            float accuracy = location.getAccuracy();
            long time = location.getTime();

            Log.d(LOG_TAG, "Monitor Location - Location : provider: "
                    + provider
                    + ". Long/lat: "
                    + longitude + "/" + latitude
                    + ". Accuracy: " + accuracy
                    + ". Time: " + time);
        }
    }

    public void onSingleLocation(MenuItem item) {
        Log.d(LOG_TAG, "Monitor Location - Single Location");

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        mNetworkListener = new MyLocationListener();
        lm.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mNetworkListener, null);

        mGpsListener = new MyLocationListener();
        lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, mGpsListener, null);

    }

    public void onExit(MenuItem item) {
        Log.d(LOG_TAG, "Monitor Location - Exit");

        doStopListening();

        finish();
    }

    private void doStopListening() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (mNetworkListener != null) {
            lm.removeUpdates(mNetworkListener);
            mNetworkListener = null;
        }

        if (mGpsListener != null) {
            lm.removeUpdates(mGpsListener);
            mGpsListener = null;
        }

        // Stop the status receiver
        if (mStatusReceiver != null) {
            mStatusReceiver.stop(this);
            mStatusReceiver = null;
        }
    }

    public void onToAsyncActivity(MenuItem item) {
        Intent intent= new Intent(this, AsyncLocationActivity.class);
        startActivity(intent);
    }
}
