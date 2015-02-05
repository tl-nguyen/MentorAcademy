package com.tlnguyen.classdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import java.net.HttpURLConnection;

/**
 * Created by tl on 05.02.15.
 */
public class CustomAsyncTask extends AsyncTask<String, Void, Integer> {

    Context mContext;

    public CustomAsyncTask(Context context) {
        mContext = context;

    }

    @Override
    protected Integer doInBackground(String... params) {

        HttpURLConnection connection;

        Handler handler = new Handler(mContext.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "using asyncTask", Toast.LENGTH_SHORT).show();
            }
        });

        return 0;
    }
}
