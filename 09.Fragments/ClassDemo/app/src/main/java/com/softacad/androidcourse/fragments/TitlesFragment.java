package com.softacad.androidcourse.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Stefan on 2.10.2014 г..
 */

// Our "top-level" fragment, showing a list of items that the user can pick.
// Upon picking an item, it takes care of displaying the data to the user as appropriate based on the current UI layout.
// The list of items displayed are managed by an adapter similar to ListActivity.
// Provides several methods for managing a list view, such as the onListItemClick() callback to handle click events.

public class TitlesFragment extends ListFragment {
    boolean mDualPane;
    int mCurCheckPosition = 0;

    // onActivityCreated() is called when the activity's onCreate() method has returned.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        Log.d(Constants.TAG, "TitlesFragment:onActivityCreated");

        // Populate list with all the titles we have in the Constants.TITLES.
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, Constants.TITLES));

        // Check to see if we have a frame in which to embed the details fragment directly in the containing UI.
        // detailsFrame won't be null at the first time the phone is switched to landscape mode.
        View detailsFrame = getActivity().findViewById(R.id.details);
        Log.d(Constants.TAG, "detailsFrame " + detailsFrame);

        // Check that a view exists and is visible.
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        Log.d(Constants.TAG, "mDualPane " + mDualPane);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("currentChoice", 0);
        }

        // Highlight the selected item in the list view .
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if (mDualPane) {
            showDetails(mCurCheckPosition);
        } else {
            getListView().setItemChecked(mCurCheckPosition, true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(Constants.TAG, "onSaveInstanceState");
        outState.putInt("currentChoice", mCurCheckPosition);
    }

    // If the user clicks on an item in the list then the onListItemClick() method is called.
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Log.d(Constants.TAG, "onListItemClick position is" + position);
        showDetails(position);
    }

    // Helper function to show the details of a selected item, either by displaying a fragment
    // in-place in the current UI, or starting a whole new activity in which it is displayed.

    void showDetails(int index) {

        mCurCheckPosition = index;

        if (mDualPane) {
            // Display both titles and content, side to side. Highlight the current selection.
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != index) {

                Log.d(Constants.TAG, "ShowDetails dual-pane: create and replace fragment");

                // Make new fragment to show this selection.
                details = DetailsFragment.newInstance(index);

                // Execute a transaction, replacing any existing fragment with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            // Launch new DetailsActivity to display the dialog fragment with selected text. Put selected index as Extra.
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }
}