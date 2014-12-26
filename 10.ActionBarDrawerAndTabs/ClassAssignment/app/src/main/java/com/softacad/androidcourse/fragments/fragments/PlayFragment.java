package com.softacad.androidcourse.fragments.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softacad.androidcourse.fragments.R;

/**
 * Created by TL on 12/22/2014.
 */
public class PlayFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.play_fragment, container, false);

        return rootView;
    }
}
