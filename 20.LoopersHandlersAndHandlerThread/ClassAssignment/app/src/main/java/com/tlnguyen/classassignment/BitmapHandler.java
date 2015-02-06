package com.tlnguyen.classassignment;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

/**
 * Created by tl on 05.02.15.
 */
public class BitmapHandler extends HandlerThread {

    private Handler handler;

    public BitmapHandler(String name) {
        super(name);
        prepareHandler();
    }

    private void prepareHandler() {
        handler = new Handler(Looper.getMainLooper());
    }

    public void postTask(Runnable runnable) {
        Log.d("Download" + BitmapHandler.class.getSimpleName(), "Handler");

        handler.post(runnable);
    }
}
