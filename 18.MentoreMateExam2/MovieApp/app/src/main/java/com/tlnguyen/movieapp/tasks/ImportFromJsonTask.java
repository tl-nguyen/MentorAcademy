package com.tlnguyen.movieapp.tasks;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.tlnguyen.movieapp.data.Constants;
import com.tlnguyen.movieapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImportFromJsonTask extends AsyncTask<Void, Void, Void> {

    private Activity mHostActivity;
    private List<Movie> mMovies;
    private ArrayAdapter<Movie> mAdapter;

    private String mQuery;

    public ImportFromJsonTask(Activity activity, List<Movie> movies, ArrayAdapter<Movie> adapter, String query) {
        this.mHostActivity = activity;
        this.mMovies = movies;
        this.mAdapter = adapter;
        mQuery = query;
    }

    @Override
    protected Void doInBackground(Void... params) {

        ArrayList<Movie> importedMovies = getMoviesFromJSON();

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

    private ArrayList<Movie> getMoviesFromJSON() {
        ArrayList<Movie> importedMovies = new ArrayList<>();

        InputStream inputStream = null;
        String jsonString = null;

        JSONArray ideas = null;

        try {
            inputStream = mHostActivity.getAssets().open("movies.json");
            int size = inputStream.available();
            byte[] byteArray = new byte[size];
            inputStream.read(byteArray);

            jsonString = new String(byteArray, "UTF-8");

            ideas = new JSONArray(jsonString);

            // Get the movies filtered by the edit text field
            for (int i = 0; i < ideas.length(); i++) {
                String name = ideas.getJSONObject(i).getString(Constants.COL_NAME);
                String description = ideas.getJSONObject(i).getString(Constants.COL_DESCRIPTION);

                // if the queried text is not empty and doesn't match quit the movie name and description
                // so we don't put it in the Db
                if (mQuery != null && !mQuery.isEmpty()) {
                    if (!name.contains(mQuery) && !description.contains(mQuery)) {
                        continue;
                    }
                }

                Movie importedIdea = new Movie(name,description);

                importedMovies.add(importedIdea);
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

        return importedMovies;
    }
}
