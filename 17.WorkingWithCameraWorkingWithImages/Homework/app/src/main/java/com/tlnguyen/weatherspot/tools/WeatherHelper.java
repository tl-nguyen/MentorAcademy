package com.tlnguyen.weatherspot.tools;

import android.location.Location;

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

/**
 * Created by tl on 02.02.15.
 */
public class WeatherHelper {
    public String getWeather(Location location) {
        String temp = null;
        String humidity = null;
        String pressure = null;
        String weather = null;

        String jsonData = getJson(location);

        try {
            JSONObject stationObject = new JSONArray(jsonData).getJSONObject(0);
            JSONObject main = stationObject.getJSONObject("last")
                    .getJSONObject("main");

            if (!main.isNull("temp")) {
                temp = main.getString("temp");
            }
            if (!main.isNull("humidity")) {
                humidity = main.getString("humidity");
            }
            if (!main.isNull("pressure")) {
                pressure = main.getString("pressure");
            }

            weather = (temp != null ? "Temperature: " + temp : "")
                    + (humidity != null ? " | Humidity: " + humidity : "")
                    + (pressure != null ? " | Pressure: " + pressure : "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weather;
    }

    private String getJson(Location location) {
        String resultString = "";

        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/station/find?lat="
                    + location.getLatitude()
                    + "&lon="
                    + location.getLongitude()
                    + "&cnt=1");

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

        } catch (IOException e) {
            return e.toString();
        }

        return resultString;
    }
}
