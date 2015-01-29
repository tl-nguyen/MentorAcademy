package com.tlnguyen.movieapp.tasks;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.tlnguyen.movieapp.data.Constants;
import com.tlnguyen.movieapp.models.Movie;

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
import java.util.ArrayList;
import java.util.List;

public class ImportFromInternetTask extends AsyncTask<Void, Void, Void> {

    private static final String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.j" +
                                        "son?apikey=9yy23ujvh42vj5gujrm8gbuu&q=android";
    private Activity mHostActivity;
    private List<Movie> mMovies;
    private ArrayAdapter<Movie> mAdapter;

    public ImportFromInternetTask(Activity activity, List<Movie> movies, ArrayAdapter<Movie> adapter) {
        this.mHostActivity = activity;
        this.mMovies = movies;
        this.mAdapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... params) {

        ArrayList<Movie> importedMovies = getMoviesFromInternet();

        saveMoviesToDb(importedMovies);

        new FetchFromDbTask(mHostActivity, mMovies, mAdapter).execute();

        return null;
    }

    private void saveMoviesToDb(ArrayList<Movie> importedMovies) {

        for (Movie movie: importedMovies) {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_NAME, movie.getName());
            values.put(Constants.COL_DESCRIPTION, movie.getDescription());

            mHostActivity.getContentResolver().insert(Constants.CONTENT_URI, values);
        }
    }

    private ArrayList<Movie> getMoviesFromInternet() {
        String resultString = "";
        ArrayList<Movie> importedMovies;

        try {
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

            importedMovies = handleJSON(resultString);

        } catch (IOException e) {
            return null;
        }

        return importedMovies;
    }

    private ArrayList<Movie> handleJSON(String resultString) {

        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            JSONObject jsonObject = new JSONObject(resultString);
            JSONArray moviesArray = jsonObject.getJSONArray("movies");

            for (int i = 0; i < moviesArray.length(); i ++) {
                Movie movie = new Movie();

                JSONObject movieObject = moviesArray.getJSONObject(i);

                movie.setName(movieObject.getString("title"));
                movie.setDescription("Year: " + movieObject.getString("year")
                        + "\nRunTime: " + movieObject.getString("runtime"));
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
