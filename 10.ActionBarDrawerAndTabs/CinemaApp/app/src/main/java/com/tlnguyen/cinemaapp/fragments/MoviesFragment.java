package com.tlnguyen.cinemaapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.activities.MovieDetailActivity;
import com.tlnguyen.cinemaapp.adapters.MovieAdapter;
import com.tlnguyen.cinemaapp.models.Movie;

import java.util.List;

public class MoviesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private List<Movie> mMovies;

    private ListView mLvMovies;

    private MovieAdapter mMovieAdapter;

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        initData();
        initViews(rootView);

        return rootView;
    }

    private void initData() {
        ParseQuery<Movie> query = new ParseQuery<Movie>("Movie");
        try {
            mMovies = query.find();
            mMovieAdapter = new MovieAdapter(getActivity(), mMovies);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initViews(View rootView) {
        mLvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        mLvMovies.setAdapter(mMovieAdapter);
        mLvMovies.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goToMovieDetail(position);
    }

    private void goToMovieDetail(int position) {
        Intent movieDetailIntent = new Intent(getActivity(), MovieDetailActivity.class);
        movieDetailIntent.putExtra("MOVIE_ID", mMovies.get(position).getObjectId());
        startActivity(movieDetailIntent);
    }
}
