package com.tlnguyen.classdemo;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by tl on 05.02.15.
 */
public class CustomHandlerThread extends HandlerThread {

    private Handler handler;

    public CustomHandlerThread(String name) {
        super(name);
    }

    public void prepareHandler() {
        handler = new Handler(getLooper());
    }

    public void postTask(Runnable runnable) {
        handler.post(runnable);
    }
}
