package com.tlnguyen.classassignment.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.tlnguyen.classassignment.models.Day;

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

public class WeatherUpdateService extends IntentService {

    public static final String BROADCAST_RESULT = "com.tlnguyen.classassignment.services.action.BROADCAST_RESULT";

    private ArrayList<Day> mDays;

    public WeatherUpdateService() {
        super("WeatherUpdateService");
        mDays = new ArrayList<>();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(WeatherUpdateService.class.getSimpleName(), "Updating...");

        String resultString = "";
        mDays.clear();

        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?lat=42.69751&lon=23.32415&cnt=10&mode=json");

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
                sb.append(line).append("\n");
            }
            resultString = sb.toString();

            if(inputStream != null) {
                inputStream.close();
            }

            mDays.addAll(handleJSON(resultString));

        } catch (IOException e) {
            Log.e(WeatherUpdateService.class.getSimpleName(), e.toString());
        }

        Log.i(WeatherUpdateService.class.getSimpleName(), "Done");

        Intent broadcastIntent = new Intent(BROADCAST_RESULT);
        broadcastIntent.putParcelableArrayListExtra("RESULT", mDays);

        sendBroadcast(broadcastIntent);

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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return daysObjs;
    }
}
