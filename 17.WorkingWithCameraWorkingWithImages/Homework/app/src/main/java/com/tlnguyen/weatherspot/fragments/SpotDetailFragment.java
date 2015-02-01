package com.tlnguyen.weatherspot.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tlnguyen.weatherspot.R;
import com.tlnguyen.weatherspot.models.Spot;

/**
 * A fragment representing a single Spot detail screen.
 * This fragment is either contained in a {@link com.tlnguyen.weatherspot.activities.SpotDetailActivity}
 * in two-pane mode (on tablets) or a {@link com.tlnguyen.weatherspot.activities.SpotDetailActivity}
 * on handsets.
 */
public class SpotDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String SPOT = "SPOT";

    /**
     * The dummy content this fragment is presenting.
     */
    private Spot mSpot;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SpotDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(SPOT)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mSpot = getArguments().getParcelable(SPOT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spot_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mSpot != null) {
            ((TextView) rootView.findViewById(R.id.spot_detail)).setText(mSpot.getLatitude() + "/" + mSpot.getLongitude());
        }

        return rootView;
    }
}
