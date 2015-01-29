package com.tlnguyen.movieapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tlnguyen.movieapp.R;

import com.tlnguyen.movieapp.models.Movie;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link com.tlnguyen.movieapp.activities.MainActivity}
 * in two-pane mode (on tablets) or a {@link com.tlnguyen.movieapp.activities.MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String MOVIE = "MOVIE";

    /**
     * The dummy content this fragment is presenting.
     */
    private Movie mMovie;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(MOVIE)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mMovie = getArguments().getParcelable(MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mMovie != null) {
            ((TextView) rootView.findViewById(R.id.tvDescription)).setText(mMovie.getDescription());
        }

        return rootView;
    }
}
