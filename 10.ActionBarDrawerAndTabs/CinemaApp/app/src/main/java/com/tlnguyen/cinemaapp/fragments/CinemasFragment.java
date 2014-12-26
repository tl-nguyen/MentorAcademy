package com.tlnguyen.cinemaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlnguyen.cinemaapp.R;

public class CinemasFragment extends Fragment {

    public CinemasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cinemas, container, false);
        return rootView;
    }
}

