package com.tlnguyen.cinemaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.adapters.MovieAdapter;
import com.tlnguyen.cinemaapp.models.Movie;

import java.util.List;

public class MoviesFragment extends Fragment {

    private List<Movie> mMovies;

    private ListView mLvMovies;

    private MovieAdapter mMovieAdapter;

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        try {
            init(rootView);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void init(View rootView) throws ParseException {
        mLvMovies = (ListView) rootView.findViewById(R.id.lvMovies);

        ParseQuery<Movie> query = new ParseQuery<Movie>("Movie");
        mMovies = query.find();
        mMovieAdapter = new MovieAdapter(getActivity(), mMovies);
        mLvMovies.setAdapter(mMovieAdapter);
    }
}
