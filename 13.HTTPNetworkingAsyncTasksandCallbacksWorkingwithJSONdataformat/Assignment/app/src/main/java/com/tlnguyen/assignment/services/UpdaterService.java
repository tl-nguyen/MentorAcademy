package com.tlnguyen.assignment.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdaterService extends Service {
    public static final String STATE_CHANGE = "android.net.wifi.STATE_CHANGE";
    public final static String BROADCAST_RESULT = "com.tlnguyen.assignment.BROADCAST_RESULT";

    public UpdaterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (STATE_CHANGE.equals(intent.getAction())) {
            new WorkerThread().start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private class WorkerThread extends Thread {

        @Override
        public void run() {
            super.run();
            updateDB();
        }
    }

    private void updateDB() {

    }
}
