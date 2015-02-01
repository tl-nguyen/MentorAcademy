package com.tlnguyen.weatherspot.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tlnguyen.weatherspot.R;
import com.tlnguyen.weatherspot.fragments.SpotListFragment;
import com.tlnguyen.weatherspot.fragments.SpotWeatherFragment;

import java.util.Locale;

/**
 * Created by tl on 01.02.15.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final static String SPOT_WEATHER = "SPOT_WEATHER";
    private final static String HISTORY = "HISTORY";

    private Context mContext;
    private FragmentManager mFragmentManager;

    private SpotWeatherFragment mSpotWeatherFragment;
    private SpotListFragment mSpotListFragment;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0:
                mSpotWeatherFragment = (SpotWeatherFragment) mFragmentManager.findFragmentByTag(SPOT_WEATHER);

                if (mSpotWeatherFragment == null) {
                    mSpotWeatherFragment = SpotWeatherFragment.newInstance(position + 1);
                }

                return mSpotWeatherFragment;
            case 1:
                mSpotListFragment = (SpotListFragment) mFragmentManager.findFragmentByTag(HISTORY);

                if (mSpotListFragment == null) {
                    mSpotListFragment = SpotListFragment.newInstance(position + 1);
                }

                return mSpotListFragment;
        }

        return null;
    }

    public boolean isTabletMode() {
        if (mSpotListFragment != null) {
            return mSpotListFragment.isTabletMode();
        }

        return false;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }
}
