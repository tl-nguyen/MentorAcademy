package com.tlnguyen.cinemaapp.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.tlnguyen.cinemaapp.models.Movie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class DownloadMoviePictureTask extends AsyncTask<String, Void, Void> {

    private Movie movie;

    public DownloadMoviePictureTask(Movie movie) {
        this.movie = movie;
    }

    @Override
    protected Void doInBackground(String... urls) {
        String imageUrl = urls[0];
        byte[] img = null;

        try {
            Bitmap bmp = null;
            URL url = new URL(imageUrl);

            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 50, stream);

            img = stream.toByteArray();

            ParseFile newPhoto = new ParseFile(img);
            newPhoto.save();

            movie.setPicture(newPhoto);
            movie.saveInBackground();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}