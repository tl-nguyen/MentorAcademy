package com.tlnguyen.fragmentspratices.simplefragmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tlnguyen.fragmentspratices.R;

public class SimpleFragment extends Fragment {
    public SimpleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.simple_fragment, container, false);

        TextView tvImSimple = (TextView) rootView.findViewById(R.id.tvImSimple);
        tvImSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getString(R.string.simple_fragment_demo)
                        , Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
