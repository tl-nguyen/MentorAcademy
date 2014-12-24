package com.tlnguyen.classdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by TL on 12/22/2014.
 */
public class FragmentOne extends Fragment {

    public FragmentOne() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_layout, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.textView);

        return rootView;

    }
}
