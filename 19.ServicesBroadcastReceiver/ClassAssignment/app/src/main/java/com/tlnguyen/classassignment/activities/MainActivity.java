package com.tlnguyen.classassignment.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tlnguyen.classassignment.R;
import com.tlnguyen.classassignment.models.Day;
import com.tlnguyen.classassignment.receivers.WeatherReceiver;
import com.tlnguyen.classassignment.services.WeatherUpdateService;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private ListView mLvDays;
    private ArrayList<Day> mDays;
    private ArrayAdapter<Day> mDaysAdapter;

    private WeatherReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initViews();
    }

    private void initData() {
        mDays = new ArrayList<Day>();

        Intent intent = getIntent();

        Intent startUpdateServiceIntent = new Intent(this, WeatherUpdateService.class);
        startService(startUpdateServiceIntent);
    }

    private void initViews() {
        mLvDays = getListView();

        mDaysAdapter = new ArrayAdapter<Day>(this, android.R.layout.simple_list_item_2, android.R.id.text1, mDays) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Day day = mDays.get(position);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(day.getTemp());
                text2.setText(day.getDescription());

                return view;
            }
        };

        mLvDays.setAdapter(mDaysAdapter);

        mResultReceiver = new WeatherReceiver(mDaysAdapter, mDays);

        IntentFilter filter = new IntentFilter(WeatherUpdateService.BROADCAST_RESULT);

        registerReceiver(mResultReceiver, filter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("DAY", mDays.get(position));
        startActivity(detailIntent);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mResultReceiver);
        super.onDestroy();
    }
}
