package com.mentoracademy.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends Activity {

    ActionBar.Tab tabOne, tabTwo, tabThree;
    Fragment fragmentOne = new FragmentTabOne();
    Fragment fragmentTwo = new FragmentTabTwo();
    Fragment fragmentThree = new FragmentTabThree();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        // Asking for the default ActionBar element that our platform supports.
        ActionBar actionBar = getActionBar();

        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Setting custom tab icons.
        tabOne = actionBar.newTab().setIcon(android.R.drawable.btn_star);
        tabTwo = actionBar.newTab().setIcon(android.R.drawable.arrow_down_float);
        tabThree = actionBar.newTab().setIcon(android.R.drawable.arrow_up_float);

        // Setting tab listeners.
        tabOne.setTabListener(new TabListener(fragmentOne));
        tabTwo.setTabListener(new TabListener(fragmentTwo));
        tabThree.setTabListener(new TabListener(fragmentThree));

        // Adding tabs to the ActionBar.
        actionBar.addTab(tabOne);
        actionBar.addTab(tabTwo);
        actionBar.addTab(tabThree);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
