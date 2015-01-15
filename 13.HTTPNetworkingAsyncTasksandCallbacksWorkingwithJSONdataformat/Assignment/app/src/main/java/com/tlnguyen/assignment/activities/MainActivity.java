package com.tlnguyen.assignment.activities;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tlnguyen.assignment.R;
import com.tlnguyen.assignment.data.Constants;
import com.tlnguyen.assignment.models.Day;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ListActivity {

    private ListView mLvDays;
    private String mJsonData;
    private ArrayList<Day> mDays;
    private ArrayAdapter<Day> mDaysAdapter;
    private String url = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=42.69751&lon=23.32415&cnt=10&mode=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initViews();
    }

    private void initData() {
        mDays = new ArrayList<Day>();
        new DownloadTask().execute(url);
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
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("DAY", mDays.get(position));
        startActivity(detailIntent);
    }

    public void refresh(View v) {
        mDays.clear();
        new DownloadTask().execute(url);
        mDaysAdapter.notifyDataSetChanged();
    }

    private class DownloadTask extends AsyncTask<String, Void, ArrayList<Day>> {

        @Override
        protected void onPostExecute(ArrayList<Day> days) {
            Cursor cursor = MainActivity.this.getContentResolver().query(Constants.CONTENT_URI, null, null, null, null);

            if (!cursor.moveToFirst()) {
                Toast.makeText(MainActivity.this, "no content yet!", Toast.LENGTH_LONG).show();
            }
            else {
                do {
                    Day day = new Day();
                    day.setTemp(cursor.getString(cursor.getColumnIndex(Constants.COL_TEMP)));
                    day.setHumidity(cursor.getString(cursor.getColumnIndex(Constants.COL_HUMIDITY)));
                    day.setPressure(cursor.getString(cursor.getColumnIndex(Constants.COL_PRESSURE)));
                    day.setDescription(cursor.getString(cursor.getColumnIndex(Constants.COL_DESCRIPTION)));

                    mDays.add(day);
                    mDaysAdapter.notifyDataSetChanged();
                }
                while (cursor.moveToNext());
            }
        }

        @Override
        protected ArrayList<Day> doInBackground(String... params) {
            String resultString = "";
            ArrayList<Day> days;

            try {

                URL url = new URL(params[0]);

                HttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpGet httpGet = new HttpGet(String.valueOf(url));
                HttpResponse response = httpclient.execute(httpGet);
                HttpEntity entity = response.getEntity();

                InputStream inputStream = entity.getContent();
                InputStreamReader byteReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(byteReader, 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                resultString = sb.toString();

                if(inputStream != null) {
                    inputStream.close();
                }

                days = handleJSON(resultString);

            } catch (IOException e) {
                return null;
            }

            return days;
        }

        private ArrayList<Day> handleJSON(String resultString) {
            ArrayList<Day> daysObjs = new ArrayList<Day>();

            try {
                JSONObject jsonObject = new JSONObject(resultString);
                JSONArray days = jsonObject.getJSONArray("list");

                for (int i = 0; i < days.length(); i ++) {
                    Day day = new Day();

                    JSONObject currentDay = days.getJSONObject(i);
                    day.setTemp(currentDay.getJSONObject("temp").getString("day"));
                    day.setHumidity(currentDay.getString("humidity"));
                    day.setPressure(currentDay.getString("pressure"));
                    day.setDescription(currentDay.getJSONArray("weather").getJSONObject(0).getString("description"));
                    daysObjs.add(day);

                    saveToSQLite(day);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return daysObjs;
        }

        private void saveToSQLite(Day day) {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_TEMP, day.getTemp());
            values.put(Constants.COL_PRESSURE, day.getPressure());
            values.put(Constants.COL_HUMIDITY, day.getHumidity());
            values.put(Constants.COL_DESCRIPTION, day.getDescription());

            MainActivity.this.getContentResolver().insert(Constants.CONTENT_URI, values);
        }
    }
}
