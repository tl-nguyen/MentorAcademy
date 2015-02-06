package com.tlnguyen.classdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomAsyncTask customAsyncTask = new CustomAsyncTask(this);

        customAsyncTask.execute("http://google.com");


        CustomHandlerThread customHandlerThread = new CustomHandlerThread("CustomHandlerThread");

        customHandlerThread.start();
        customHandlerThread.prepareHandler();

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getBaseContext(), "Using handler", Toast.LENGTH_SHORT).show();
            }
        };

        customHandlerThread.postTask(runnable);

    }

}
