package com.tlnguyen.movieapp.tasks;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.tlnguyen.movieapp.data.Constants;
import com.tlnguyen.movieapp.models.Movie;

import java.util.List;

public class FetchFromDbTask extends AsyncTask<Void, Void, Void>{

    private Activity mHostActivity;
    private List<Movie> mMovies;
    private ArrayAdapter<Movie> mAdapter;


    public FetchFromDbTask(Activity activity, List<Movie> movies, ArrayAdapter<Movie> adapter) {
        this.mHostActivity = activity;
        this.mMovies = movies;
        this.mAdapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... params) {
        mMovies.clear();

        Cursor cursor = mHostActivity.getContentResolver().query(Constants.CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Constants.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(Constants.COL_NAME));
                String description = cursor.getString(cursor.getColumnIndex(Constants.COL_DESCRIPTION));

                Movie currentMovie = new Movie(id, name, description);
                mMovies.add(currentMovie);
            }
            while (cursor.moveToNext());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mAdapter.notifyDataSetChanged();
    }
}
