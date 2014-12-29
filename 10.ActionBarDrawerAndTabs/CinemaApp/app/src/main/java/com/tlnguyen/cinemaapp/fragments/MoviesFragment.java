package com.tlnguyen.cinemaapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.activities.MovieDetailActivity;
import com.tlnguyen.cinemaapp.adapters.MovieAdapter;
import com.tlnguyen.cinemaapp.commons.Constants;
import com.tlnguyen.cinemaapp.models.Cinema;
import com.tlnguyen.cinemaapp.models.Movie;
import com.tlnguyen.cinemaapp.models.MovieCinema;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private List<Movie> mMovies;
    private String mCinemaId;

    private ListView mLvMovies;

    private MovieAdapter mMovieAdapter;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_movies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_clear:
                // Reset to show all the movies
                resetMovies();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetMovies() {
        retrieveAllMovies();
        mLvMovies.setAdapter(mMovieAdapter);
        mCinemaId = null;
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

        mCinemaId = getActivity().getIntent().getStringExtra(Constants.CINEMA_ID);

        if (mCinemaId != null) {
            // Show the movies available for this Cinema
            retrieveMoviesByCinema(mCinemaId);
        }
        else {
            // Show all the movies
            retrieveAllMovies();
        }
    }

    private void retrieveAllMovies() {
        ParseQuery<Movie> query = new ParseQuery<Movie>(Constants.MOVIE_CLASS_NAME);
        try {
            mMovies = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mMovieAdapter = new MovieAdapter(getActivity(), mMovies);
    }

    private void retrieveMoviesByCinema(String cinemaId) {
        Cinema cinema = null;

        // Request Cinema object first
        ParseQuery<Cinema> cinemaQuery = new ParseQuery<Cinema>(Constants.CINEMA_CLASS_NAME);
        cinemaQuery.whereEqualTo(Constants.ID_FIELD_NAME, cinemaId);

        try {
            cinema = cinemaQuery.find().get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Request all the movies related to the cinema
        ParseQuery<MovieCinema> movieCinemasQuery = new ParseQuery<MovieCinema>("MovieCinema");
        movieCinemasQuery.whereEqualTo("cinema", cinema);

        try {
            List<MovieCinema> movieCinemas = movieCinemasQuery.find();
            mMovies = new ArrayList<Movie>();

            for (MovieCinema movieCinema: movieCinemas) {
                // Set the related movies to mMovies
                mMovies.add(movieCinema.getMovie());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        mMovieAdapter = new MovieAdapter(getActivity(), mMovies);
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
        movieDetailIntent.putExtra(Constants.MOVIE_ID, mMovies.get(position).getObjectId());
        if (mCinemaId != null) {
            movieDetailIntent.putExtra(Constants.CINEMA_ID, mCinemaId);
        }
        startActivity(movieDetailIntent);
    }
}
