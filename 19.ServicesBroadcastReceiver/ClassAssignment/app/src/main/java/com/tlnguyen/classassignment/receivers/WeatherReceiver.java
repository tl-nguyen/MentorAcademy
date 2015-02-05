package com.tlnguyen.classassignment.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.tlnguyen.classassignment.models.Day;
import com.tlnguyen.classassignment.services.WeatherUpdateService;

import java.util.ArrayList;

public class WeatherReceiver extends BroadcastReceiver {

    ArrayAdapter<Day> mAdapter;
    ArrayList<Day> mDays;

    public WeatherReceiver(ArrayAdapter<Day> adapter, ArrayList<Day> days) {
        this.mAdapter = adapter;
        this.mDays = days;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equalsIgnoreCase(WeatherUpdateService.BROADCAST_RESULT)) {
            updateUI(intent);
        }
    }

    private void updateUI(Intent intent) {
        Log.d(WeatherReceiver.class.getSimpleName(), "Update UI....");

        ArrayList<Day> days = intent.getParcelableArrayListExtra("RESULT");
        mDays.clear();
        mDays.addAll(days);
        mAdapter.notifyDataSetChanged();

        Log.d(WeatherReceiver.class.getSimpleName(), "Update UI - Done");
    }
}
