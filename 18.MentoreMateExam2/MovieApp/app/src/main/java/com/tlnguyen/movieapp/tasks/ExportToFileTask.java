package com.tlnguyen.movieapp.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.tlnguyen.movieapp.R;
import com.tlnguyen.movieapp.models.Movie;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by tl on 29.01.15.
 */
public class ExportToFileTask extends AsyncTask<Void, Void, Void>{

    private List<Movie> mMovies;
    private Activity mHostActivity;

    private String filename = "MOVIES_EXPORTED";
    private String fileExtension = ".txt";
    private String file = filename + fileExtension;
    private StringBuilder fileContent = new StringBuilder();

    public ExportToFileTask(Activity activity, List<Movie> movies) {
        this.mMovies = movies;
        this.mHostActivity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        buildContent();
        writeToExternalStorageFile(file);

        return null;
    }

    private void buildContent() {

        fileContent.append("[");

        for (Movie movie: mMovies) {
            fileContent.append("{");

            fileContent.append("\"name\":\"").append(movie.getName()).append("\"");
            fileContent.append("\"description\":\"").append(movie.getDescription()).append("\"");

            fileContent.append("}");
        }

        fileContent.append("]");
    }

    private void writeToExternalStorageFile(String fileName) {
        File file;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            file = new File(mHostActivity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(fileContent.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(mHostActivity,
                mHostActivity.getString(R.string.export_complete),
                Toast.LENGTH_LONG).show();
    }
}
