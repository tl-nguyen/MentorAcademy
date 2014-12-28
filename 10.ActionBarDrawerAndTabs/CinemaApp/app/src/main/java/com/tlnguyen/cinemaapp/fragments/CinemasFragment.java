package com.tlnguyen.cinemaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tlnguyen.cinemaapp.R;
import com.tlnguyen.cinemaapp.adapters.CinemaAdapter;
import com.tlnguyen.cinemaapp.models.Cinema;

import java.util.List;

public class CinemasFragment extends Fragment {

    private List<Cinema> mCinemas;

    private GridView mGvCinemas;

    private CinemaAdapter mCinemaAdapter;

    public CinemasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cinemas, container, false);

        try {
            init(rootView);
        } catch (ParseException e) {
//            e.printStackTrace();
        }

        return rootView;
    }

    private void init(View rootView) throws ParseException {
        mGvCinemas = (GridView) rootView.findViewById(R.id.gvCinemas);

        ParseQuery<Cinema> query = new ParseQuery<Cinema>("Cinema");
        mCinemas = query.find();
        mCinemaAdapter = new CinemaAdapter(getActivity(), mCinemas);
        mGvCinemas.setAdapter(mCinemaAdapter);
    }
}

