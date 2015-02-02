package com.tlnguyen.weatherspot.fragments;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.tlnguyen.weatherspot.R;
import com.tlnguyen.weatherspot.activities.SpotDetailActivity;
import com.tlnguyen.weatherspot.data.Constants;
import com.tlnguyen.weatherspot.models.Spot;
import com.tlnguyen.weatherspot.tools.LocationHelper;
import com.tlnguyen.weatherspot.tools.WeatherHelper;

import java.util.Date;

/**
 * Created by tl on 01.02.15.
 */
public class SpotWeatherFragment extends Fragment implements View.OnClickListener, LocationListener{

    private static final String LOG_TAG = "WeatherSpot";
    private static final String SPOT = "SPOT";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SpotWeatherFragment newInstance(int sectionNumber) {
        SpotWeatherFragment fragment = new SpotWeatherFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView mTvLocation;
    private TextView mTvWeather;
    private Button mBtnQuery;

    private Looper mLooper;
    private Location mCurrentLocation;
    private String mCurrentSpotWeather;

    LocationHelper mLocationHelper;
    WeatherHelper mWeatherHelper;

    public SpotWeatherFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mLocationHelper = new LocationHelper(getActivity());
        mWeatherHelper = new WeatherHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spot_weather, container, false);

        init(rootView);

        return rootView;
    }

    private void init(View rootView) {
        mTvLocation = (TextView) rootView.findViewById(R.id.tvLocation);
        mTvWeather = (TextView) rootView.findViewById(R.id.tvWeather);
        mBtnQuery = (Button) rootView.findViewById(R.id.btnQuery);

        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnQuery:
                getCoordinateUpdate();
                break;
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        Log.d(LOG_TAG, "onLocationChanged");

        mCurrentLocation = location;
        mCurrentSpotWeather = mWeatherHelper.getWeather(location);

        final Spot newSpot = new Spot(new LatLng(location.getLatitude(),
                location.getLongitude()),
                mCurrentSpotWeather,
                null,
                new Date());

        saveToDb(newSpot);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setUI();
                notifyTheUser(newSpot);
            }
        });
    }

    private void saveToDb(Spot newSpot) {
        ContentValues values = new ContentValues();
        values.put(Constants.COL_LATITUDE, newSpot.getLocation().latitude);
        values.put(Constants.COL_LONGITUDE, newSpot.getLocation().longitude);
        values.put(Constants.COL_CREATED_AT, newSpot.getCreatedAt().toString());
        values.put(Constants.COL_PHOTO_PATH, newSpot.getPhotoPath());
        values.put(Constants.COL_WEATHER, newSpot.getWeather());

        getActivity().getContentResolver().insert(Constants.CONTENT_URI, values);
    }

    private void setUI() {
        if (mCurrentLocation != null) {
            mTvLocation.setText(mLocationHelper.getAddress(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
            mTvWeather.setText(mCurrentSpotWeather);
        }
    }

    // Notification
    public void notifyTheUser(Spot spot) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity())
                .setContentTitle(spot.getId() + " : " + spot.getLocation())
                .setContentText(spot.getWeather())
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);

        Intent intent = new Intent(getActivity(), SpotDetailActivity.class);
        intent.putExtra(SPOT, spot);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Activity.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(LOG_TAG, "onStatusChanged");

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(LOG_TAG, "onProviderEnabled");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(LOG_TAG, "onProviderDisabled");

    }

    public void getCoordinateUpdate() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Make a separate thread to run and then get the looper of the thread
        // and pass it to lm.requestLocationUpdates for the listening process to run on this thread
        if (mLocationHelper.confirmNetworkProviderAvailable(lm)) {
            HandlerThread thread = new HandlerThread("locationthread");
            thread.start();
            mLooper = thread.getLooper();

            lm.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, mLooper);
        }
    }
}
