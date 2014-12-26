package com.tlnguyen.fragmentspratices.viewpagerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlnguyen.fragmentspratices.R;

/**
 * Created by TL on 12/22/2014.
 */
public class ViewPagerFragment extends Fragment {
    public ViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.viewpager_fragment, container, false);
        return rootView;
    }
}
