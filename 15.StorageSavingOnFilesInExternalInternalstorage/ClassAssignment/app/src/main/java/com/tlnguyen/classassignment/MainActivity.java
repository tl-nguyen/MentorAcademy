package com.tlnguyen.classassignment;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.tlnguyen.classassignment.logger.Log;
import com.tlnguyen.classassignment.logger.LogMode;

import java.util.ArrayList;


public class MainActivity extends ListActivity {

    private ListView mLvLogs;
    private Spinner mSFilters;

    private ArrayAdapter<String> mLogsAdapter;

    private ArrayList<String> mLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        populateFile();
        initData();
        initViews();
    }

    private void initViews() {
        mLvLogs = getListView();
        mSFilters = (Spinner) findViewById(R.id.sFilters);

        // Spinner
        ArrayAdapter<CharSequence> filtersAdapter = ArrayAdapter.createFromResource(this,
                R.array.log_modes, android.R.layout.simple_spinner_item);

        filtersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSFilters.setAdapter(filtersAdapter);

        mSFilters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                mLogs.clear();

                switch (item) {
                    case "ALL":
                        mLogs.addAll(Log.read(MainActivity.this, null));
                        break;
                    case "CRITICAL":
                        mLogs.addAll(Log.read(MainActivity.this, LogMode.CRITICAL));
                        break;
                    case "NORMAL":
                        mLogs.addAll(Log.read(MainActivity.this, LogMode.NORMAL));
                        break;
                }

                mLogsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // List View
        mLogsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLogs);
        mLvLogs.setAdapter(mLogsAdapter);
    }

    private void initData() {
        mLogs = Log.read(this, null);
    }

    private void populateFile() {
        Log.normal(this, "test normal log");
        Log.critical(this, "test critical log");
        Log.normal(this, "test normal 2 log");
        Log.critical(this, "test critical 2 log");
        Log.normal(this, "test normal 3 log");
        Log.critical(this, "test critical 3 log");
        Log.normal(this, "test normal 4 log");
        Log.critical(this, "test critical 4 log");
    }
}
