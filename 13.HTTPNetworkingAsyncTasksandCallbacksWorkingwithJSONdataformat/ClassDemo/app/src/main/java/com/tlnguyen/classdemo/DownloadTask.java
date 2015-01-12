package com.tlnguyen.classdemo;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by TL on 1/12/2015.
 */
public class DownloadTask extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        Log.d("ASYNCTASK", "onPreExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Log.d("ASYNCTASK", "onProgressUpdate");

    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("ASYNCTASK", s);
    }

    @Override
    protected String doInBackground(String... params) {
        String resultString = "";

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

        } catch (IOException e) {
            return e.toString();
        }

        return resultString;
    }
}
