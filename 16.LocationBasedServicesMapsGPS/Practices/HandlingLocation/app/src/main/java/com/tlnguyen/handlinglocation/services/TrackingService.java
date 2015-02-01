package com.tlnguyen.handlinglocation.services;

import android.app.Service;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

public class TrackingService extends Service {

    private final String LOG_TAG = "Monitor Location";

    public static final String ACTION_START_MONITORING = "com.tlnguyen.START_MONITORING";
    public static final String ACTION_STOP_MONITORING = "com.tlnguyen.STOP_MONITORING";

    private final static String HANDLER_THREAD_NAME = "MyLocationThread";

    LocationListener mLocationListener;
    Looper mLooper;
    Handler mHandler;

    public TrackingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }
}
