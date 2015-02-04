package com.tlnguyen.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WorkerService extends Service {

    public final static String ACTION_ASYNC="com.tlnguyen.servicesdemo.ACTION_ASYNC";
    public final static String BROADCAST_RESULT = "com.tlnguyen.servicesdemo.BROADCAST_RESULT";
    public final static String KEY_MESSAGE = "KEY_MESSAGE";

    public WorkerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ACTION_ASYNC.equals(intent.getAction())) {
            new WorkerThread().start();
        }
        else {
            blockingPrint();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void blockingPrint() {
        int counter = 5;
        while (counter-- > 0) {
            String message = "counter " + counter;
            Log.d(WorkerService.class.getSimpleName(), message);

            Intent intent = new Intent(BROADCAST_RESULT);
            intent.putExtra(KEY_MESSAGE, message);

            // Using LocalBroadcast
//            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

            // Using normal broadcast
            sendBroadcast(intent);

            try {
                Thread.sleep(counter * 1000);
            } catch (InterruptedException e) {

            }
        }
    }

    private class WorkerThread extends Thread {

        @Override
        public void run() {
            super.run();
            blockingPrint();
        }
    }
}
