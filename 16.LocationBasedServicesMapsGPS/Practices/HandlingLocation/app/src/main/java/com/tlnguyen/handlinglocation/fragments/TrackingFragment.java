package com.tlnguyen.handlinglocation.fragments;

/**
 * Created by tl on 30.01.15.
 */

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tlnguyen.handlinglocation.R;
import com.tlnguyen.handlinglocation.services.TrackingService;

// We can implements the LocationListener here because the fragment is retained and it won't get destroyed
public class TrackingFragment extends Fragment implements LocationListener {

    private final String LOG_TAG = "Monitor Location";

    private Looper mLooper;

    private TextView mTvLocation;

    public TrackingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        // make this a retained fragment for rotation issues
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_async_location, container, false);

        mTvLocation = (TextView) rootView.findViewById(R.id.tvLocation);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_start_tracking:
                onMenuStartTracking(item);
                break;
            case R.id.action_stop_tracking:
                onMenuStopTracking(item);
                break;
            case R.id.action_stop_tracking_service:
                onMenuStartTrackingService(item);
                break;
            case R.id.action_start_tracking_service:
                onMenuStopTrackingService(item);
                break;
        }

        return true;
    }

    private void onMenuStartTrackingService(MenuItem item) {
        getActivity().startService(new Intent(TrackingService.ACTION_START_MONITORING));
    }

    private void onMenuStopTrackingService(MenuItem item) {
        getActivity().startService(new Intent(TrackingService.ACTION_STOP_MONITORING));
    }

    private void onMenuStopTracking(MenuItem item) {
        doStopTracking();
    }

    private void onMenuStartTracking(MenuItem item) {
        doStartTracking();
    }

    private void doStartTracking() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Make a separate thread to run and then get the looper of the thread
        // and pass it to lm.requestLocationUpdates for the listening process to run on this thread
        HandlerThread thread = new HandlerThread("locationthread");
        thread.start();
        mLooper = thread.getLooper();

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this, mLooper);
    }

    private void doStopTracking() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(this);

        if (mLooper != null) {
            mLooper.quit();
            mLooper = null;
        }
    }

    public void setLocation(Location location) {
        mTvLocation.setText(location.getLatitude() + "/" + location.getLongitude());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(LOG_TAG, "Monitor Location - onLocationChanged");

        final Location theLocation = location;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setLocation(theLocation);
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
