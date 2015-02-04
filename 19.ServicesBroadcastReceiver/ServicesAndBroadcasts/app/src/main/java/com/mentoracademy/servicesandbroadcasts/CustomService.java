package com.mentoracademy.servicesandbroadcasts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Stefan on 31.1.2015 г..
 */
public class CustomService extends Service {

    /** indicates how to behave if the service is killed */
    int startMode;
    /** interface for clients that bind */
    IBinder binder;
    /** indicates whether onRebind should be used */
    boolean allowRebind;

    /** Called when the service is being created. */
    @Override
    public void onCreate() {

    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startMode = START_STICKY;
        // Let it continue running until it is stopped.
        Log.d("CUSTOM_SERVICE", "Custom Service Started");
        return startMode;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return allowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {

    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

        super.onDestroy();
        Log.d("CUSTOM_SERVICE", "Custom Service Destroyed");
    }

}
