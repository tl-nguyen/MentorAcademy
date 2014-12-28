package com.tlnguyen.cinemaapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.activities.CinemaDetailActivity;
import com.tlnguyen.cinemaapp.adapters.CinemaAdapter;
import com.tlnguyen.cinemaapp.models.Cinema;

import java.util.List;

public class CinemasFragment extends Fragment implements AdapterView.OnItemClickListener {

    private List<Cinema> mCinemas;

    private GridView mGvCinemas;

    private CinemaAdapter mCinemaAdapter;

    public CinemasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cinemas, container, false);

        initData();
        initViews(rootView);

        return rootView;
    }

    private void initData() {
        ParseQuery<Cinema> query = new ParseQuery<Cinema>("Cinema");
        try {
            mCinemas = query.find();
            mCinemaAdapter = new CinemaAdapter(getActivity(), mCinemas);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initViews(View rootView) {
        mGvCinemas = (GridView) rootView.findViewById(R.id.gvCinemas);
        mGvCinemas.setAdapter(mCinemaAdapter);
        mGvCinemas.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goToCinemaDetail(position);
    }

    private void goToCinemaDetail(int position) {
        Intent cinemaDetailIntent = new Intent(getActivity(), CinemaDetailActivity.class);
        cinemaDetailIntent.putExtra("CINEMA_ID", mCinemas.get(position).getObjectId());
        startActivity(cinemaDetailIntent);
    }
}

