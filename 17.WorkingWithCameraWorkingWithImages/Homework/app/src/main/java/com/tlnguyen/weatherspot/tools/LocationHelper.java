package com.tlnguyen.weatherspot.tools;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by tl on 01.02.15.
 */
public class LocationHelper {

    private Context mContext;

    public LocationHelper(Context context) {
        mContext = context;
    }

    public String getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        String result = null;

        geocoder = new Geocoder(mContext, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getAddressLine(1);
            String country = addresses.get(0).getAddressLine(2);

            result = (address != null ? address + " | " : "")
                    + (city != null ? city : "")
                    + (country != null ? " | " + country : "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Check for network provider if it's enabled or not
    public boolean confirmNetworkProviderAvailable(LocationManager lm) {
        return confirmNetworkProviderEnabled(lm) &&
                confirmAirPlaneModeIsOff(lm) &&
                confirmWifiAvailable();
    }

    private boolean confirmNetworkProviderEnabled(LocationManager lm) {
        boolean isAvailable = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isAvailable) {
            Toast.makeText(mContext, "Turn on your network provider", Toast.LENGTH_SHORT).show();

            // Go to system settings to change it
            mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        return isAvailable;
    }

    private boolean confirmAirPlaneModeIsOff(LocationManager lm) {
        boolean isOff = Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 0;

        if (!isOff) {
            Toast.makeText(mContext, "Turn off your Airplane Mode first", Toast.LENGTH_SHORT).show();

            // Go to system settings to change it
            mContext.startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
        }

        return isOff;
    }

    private boolean confirmWifiAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isAvailable = wifiInfo.isAvailable();

        if (!isAvailable) {
            Toast.makeText(mContext, "Turn on your wifi", Toast.LENGTH_SHORT).show();
            // Go to system settings to change it
            mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }

        return isAvailable;
    }
}
