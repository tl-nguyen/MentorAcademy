package com.tlnguyen.weatherspot.fragments;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tlnguyen.weatherspot.R;
import com.tlnguyen.weatherspot.tools.LocationHelper;

/**
 * Created by tl on 01.02.15.
 */
public class SpotWeatherFragment extends Fragment implements View.OnClickListener, LocationListener{

    private static final String LOG_TAG = "WeatherSpot";

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

    LocationHelper mLocationHelper;

    public SpotWeatherFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mLocationHelper = new LocationHelper(getActivity());
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
                getCoordinate();
                break;
        }
    }

    @Override
    public void onLocationChanged(final Location location) {

        mCurrentLocation = location;

        Log.d(LOG_TAG, "onLocationChanged");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setLocation();
            }
        });
    }

    private void setLocation() {
        if (mCurrentLocation != null) {
            mTvLocation.setText(mLocationHelper.getAddress(mCurrentLocation));
        }
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

    public void getCoordinate() {
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
