package com.softacad.androidcourse.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Stefan on 2.10.2014 г..
 */

// This is the secondary fragment, displaying the details of a particular item.

public class DetailsFragment extends Fragment {

    // Create a new instance of DetailsFragment, showing the text at 'index'.
    public static DetailsFragment newInstance(int index) {
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    // The onCreateView method is called the first time the fragment draws its user interface.
    // Return the View to be drawn, or null if the fragment does not provide a UI.
    // We create a scroll view and text and return a reference to the scroll which is then drawn to the screen.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(Constants.TAG, "DetailsFragment:onCreateView");

        if (container == null) {
            //There is no need to create a scroll as it won't be displayed
            return null;
        } else {
            // Container is non-null, and this is the parent view that the fragment's UI should be attached to.
            // The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
            ScrollView scroll = new ScrollView(getActivity());
            TextView text = new TextView(getActivity());

            int padding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
            text.setPadding(padding, padding, padding, padding);
            scroll.addView(text);
            text.setText(Constants.DIALOGUE[getShownIndex()]);

            return scroll;
        }
    }
}