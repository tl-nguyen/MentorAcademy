package com.tlnguyen.classassignment;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {

    public final static String IMAGE_URL = "IMAGE_URL";
    public final static String DOWNLOAD_IMG = "com.tlnguyen.classassignment.action.DOWNLOAD_IMG";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Download" + DownloadService.class.getSimpleName(), "Start Download");

        Bundle bundle = intent.getExtras();

        URL url = null;

        Bitmap bmp = null;

        int count;

        try {
            url = new URL(bundle.getString(IMAGE_URL));

            URLConnection conection = url.openConnection();
            conection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = conection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            // Output stream
            OutputStream output = new FileOutputStream(Environment
                    .getExternalStorageDirectory().toString()
                    + "/data/downloadedfile.jpg");


            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        BitmapHandler handler = new BitmapHandler("BitmapHandler");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String path = "file://" + Environment
                        .getExternalStorageDirectory().toString()
                        + "/data/downloadedfile.jpg";

                intent.setDataAndType(Uri.parse(path), "image/*");

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        Log.d("Download" + DownloadService.class.getSimpleName(), "Done Download");

        handler.postTask(runnable);
    }
}
