package com.tlnguyen.fragmentspratices.multifragmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tlnguyen.fragmentspratices.R;

public class FragmentMenu extends Fragment implements View.OnClickListener {

    private Fragment mFragment;
    private FragmentTransaction mFragmentTransaction;

    public FragmentMenu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        // Set default fragment for the first time
        mFragment = new FragmentOne();
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.container, mFragment).commit();

        Button btnFragmentOne = (Button) rootView.findViewById(R.id.btnFragmentOne);
        Button btnFragmentTwo = (Button) rootView.findViewById(R.id.btnFragmentTwo);

        btnFragmentOne.setOnClickListener(this);
        btnFragmentTwo.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnFragmentOne:
                mFragment = new FragmentOne();
                replaceContain();

                break;

            case R.id.btnFragmentTwo:
                mFragment = new FragmentTwo();
                replaceContain();

                break;
        }
    }

    private void replaceContain() {
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.container, mFragment).commit();
    }
}
