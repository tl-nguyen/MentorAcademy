package com.tlnguyen.cinemaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tlnguyen.cinemaapp.R;

public class MoviesFragment extends Fragment {

    private ListView mLvMovies;

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        init(rootView);

        return rootView;
    }

    private void init(View rootView) {
        mLvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
    }
}
