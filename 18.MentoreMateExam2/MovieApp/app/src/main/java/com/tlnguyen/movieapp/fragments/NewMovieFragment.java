package com.tlnguyen.movieapp.fragments;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tlnguyen.movieapp.R;
import com.tlnguyen.movieapp.activities.MainActivity;
import com.tlnguyen.movieapp.activities.MovieDetailActivity;
import com.tlnguyen.movieapp.data.Constants;
import com.tlnguyen.movieapp.models.Movie;

/**
 * Created by tl on 29.01.15.
 */
public class NewMovieFragment extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final String MOVIE = "MOVIE";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewMovieFragment newInstance(int sectionNumber) {
        NewMovieFragment fragment = new NewMovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private EditText mEtName;
    private EditText mEtDescription;
    private Button mBtnSave;

    public NewMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_movie, container, false);

        init(rootView);

        return rootView;
    }

    private void init(View rootView) {
        mEtName = (EditText) rootView.findViewById(R.id.etName);
        mEtDescription = (EditText) rootView.findViewById(R.id.etDescription);
        mBtnSave = (Button) rootView.findViewById(R.id.btnSave);

        mBtnSave.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set title
        ((MainActivity) getActivity()).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnSave:

                Movie savedMovie = saveToSQLite();
                notifyTheUser(savedMovie);
                clearInputs();

                break;
        }
    }

    private Movie saveToSQLite() {
        String name = mEtName.getText().toString();
        String description = mEtDescription.getText().toString();

        ContentValues values = new ContentValues();
        values.put(Constants.COL_NAME, name);
        values.put(Constants.COL_DESCRIPTION, description);

        // Insert to db and get the saved movie uri
        Uri savedMovieUri = getActivity().getContentResolver().insert(Constants.CONTENT_URI, values);

        // Get the saved movie Id from db
        int savedMovieId = Integer.parseInt(savedMovieUri.getLastPathSegment());

        return new Movie(savedMovieId, name, description);
    }

    private void clearInputs() {
        mEtName.setText("");
        mEtDescription.setText("");

        mEtName.setFocusableInTouchMode(true);
        mEtName.requestFocus();
    }

    // Notification
    public void notifyTheUser(Movie movie) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity())
                .setContentTitle(movie.getId() + " : " + movie.getName())
                .setContentText(movie.getDescription())
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);

        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MOVIE, movie);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Activity.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }
}
