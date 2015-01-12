package com.tlnguyen.classdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream inputStream = null;
        String jsonString = null;

        JSONObject jsonObject = null;

        try {
            inputStream = getAssets().open("data.json");
            int size = inputStream.available();
            byte[] byteArray = new byte[size];
            inputStream.read(byteArray);

            jsonString = new String(byteArray, "UTF-8");

            jsonObject = new JSONObject(jsonString);
            JSONArray employees = jsonObject.getJSONArray("employees");

            // Make interable employees
            JSONArrayInterable empployeesInterable = new JSONArrayInterable(employees);

            for (JSONObject employee : empployeesInterable) {
                Log.d("JSON READING", "Employee: "
                        + employee.getString("firstName")
                        + " "
                        + employee.getString("lastName"));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        new DownloadTask().execute("http://www.omdbapi.com/?t=the&y=2014&plot=full&r=json");
    }
}
