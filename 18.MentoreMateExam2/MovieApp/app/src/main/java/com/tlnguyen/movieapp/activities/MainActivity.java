package com.tlnguyen.movieapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.tlnguyen.movieapp.fragments.MovieDetailFragment;
import com.tlnguyen.movieapp.fragments.MovieListFragment;
import com.tlnguyen.movieapp.fragments.NavigationDrawerFragment;
import com.tlnguyen.movieapp.R;
import com.tlnguyen.movieapp.fragments.NewMovieFragment;
import com.tlnguyen.movieapp.models.Movie;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, MovieListFragment.Callbacks {

    private static final String NEW_MOVIE = "NEW_MOVIE";
    private static final String MY_MOVIES = "MY_MOVIES";

    private MovieListFragment mMovieListFragment;
    private NewMovieFragment mNewMovieFragment;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0:
                mNewMovieFragment = (NewMovieFragment) fragmentManager.findFragmentByTag(NEW_MOVIE);

                if (mNewMovieFragment == null) {
                    // The fragment is created for the first time
                    mNewMovieFragment = NewMovieFragment.newInstance(position + 1);
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.container, mNewMovieFragment, NEW_MOVIE)
                        .addToBackStack(NEW_MOVIE)
                        .commit();
                break;
            case 1:
                mMovieListFragment = (MovieListFragment) fragmentManager.findFragmentByTag(MY_MOVIES);

                if (mMovieListFragment == null) {
                    mMovieListFragment = MovieListFragment.newInstance(position + 1);
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.container, mMovieListFragment, MY_MOVIES)
                        .addToBackStack(MY_MOVIES)
                        .commit();

                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
     * Callback method from {@link com.tlnguyen.movieapp.fragments.MovieListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(Movie movie) {
        if (mMovieListFragment.isTabletMode()) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(MovieDetailFragment.MOVIE, movie);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, MovieDetailActivity.class);
            detailIntent.putExtra(MovieDetailFragment.MOVIE, movie);
            startActivity(detailIntent);
        }
    }

}
