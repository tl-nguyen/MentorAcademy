package com.tlnguyen.movieapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tlnguyen.movieapp.R;
import com.tlnguyen.movieapp.activities.MainActivity;
import com.tlnguyen.movieapp.models.Movie;
import com.tlnguyen.movieapp.tasks.ExportToFileTask;
import com.tlnguyen.movieapp.tasks.FetchFromDbTask;
import com.tlnguyen.movieapp.tasks.ImportFromInternetTask;
import com.tlnguyen.movieapp.tasks.ImportFromJsonTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A list fragment representing a list of Movies. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link MovieDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class MovieListFragment extends ListFragment implements View.OnClickListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(Movie movie);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MovieListFragment newInstance(int sectionNumber) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private List<Movie> mMovies;
    private boolean mTwoPane;
    private ArrayAdapter<Movie> mAdapter;

    private EditText mEtQuery;
    private Button mBtnImportJSON;
    private Button mBtnImportInternet;
    private Button mBtnExport;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovies = new ArrayList<>();

        mAdapter = new ArrayAdapter<Movie>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                mMovies) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                ((TextView) view.findViewById(android.R.id.text1)).setText(mMovies.get(position).getName());

                return view;
            }
        };

        setListAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_list_fragment, container, false);

        init(rootView);

        return rootView;
    }

    private void init(View rootView) {
        // Check if it's tablet
        mTwoPane = rootView.findViewById(R.id.movie_detail_container) != null;

        mEtQuery = (EditText) rootView.findViewById(R.id.etQuery);
        mBtnImportJSON = (Button) rootView.findViewById(R.id.btnImportJSON);
        mBtnImportInternet = (Button) rootView.findViewById(R.id.btnImportInternet);
        mBtnExport = (Button) rootView.findViewById(R.id.btnExport);

        mBtnImportJSON.setOnClickListener(this);
        mBtnImportInternet.setOnClickListener(this);
        mBtnExport.setOnClickListener(this);
    }

    public boolean isTabletMode() {
        return mTwoPane;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }

        // Set title
        ((MainActivity) getActivity()).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

        if (mTwoPane) {
            this.setActivateOnItemClick(true);
        }

        // Fetch Ideas from SQLite Db Asynchronously
        new FetchFromDbTask(getActivity(), mMovies, mAdapter).execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = null;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(mMovies.get(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.btnImportJSON:
                // Import from assets/movies.json
                new ImportFromJsonTask(getActivity(), mMovies, mAdapter, mEtQuery.getText().toString()).execute();
                break;
            case R.id.btnImportInternet:
                // Import from http://api.rottentomatoes.com/api/public/v1.0/movies.j
                //son?apikey=9yy23ujvh42vj5gujrm8gbuu&q={android}
                new ImportFromInternetTask(getActivity(), mMovies, mAdapter).execute();
                break;
            case R.id.btnExport:
                new ExportToFileTask(getActivity(), mMovies).execute();
                break;
        }
    }

}
