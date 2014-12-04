package com.tlnguyen.classdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends Activity {

    final String[] sports = {
            "Rugby",
            "Football",
            "Boxing",
            "Tennis",
            "Rowing",
            "Swimming",
            "Rugby 2",
            "Football 2",
            "Boxing 2",
            "Tennis 2",
            "Rowing 2",
            "Swimming 2"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        GridView list = (GridView) findViewById(R.id.grid);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.sport_row,
                R.id.sportName,
                sports);

        CustomAdapter customAdapter = new CustomAdapter(this);

        list.setAdapter(customAdapter);

        list.setFastScrollEnabled(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Clicked " + sports[position], Toast.LENGTH_SHORT).show();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "Long Clicked " + sports[position], Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
