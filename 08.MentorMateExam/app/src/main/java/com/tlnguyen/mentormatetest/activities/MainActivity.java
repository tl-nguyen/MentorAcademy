package com.tlnguyen.mentormatetest.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tlnguyen.mentormatetest.R;
import com.tlnguyen.mentormatetest.adapters.IdeaAdapter;
import com.tlnguyen.mentormatetest.data.DbManager;
import com.tlnguyen.mentormatetest.models.Idea;
import com.tlnguyen.mentormatetest.models.Role;
import com.tlnguyen.mentormatetest.models.User;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private User mCurrentUser;
    private List<Idea> mIdeas;
    private boolean mIsAdmin;
    private List<Idea> mSelectedIdeas;
    private List<Idea> mSearchResults;

    private ListView mLvIdeas;

    private IdeaAdapter mIdeaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mSelectedIdeas = new ArrayList<Idea>();
        mSearchResults = new ArrayList<Idea>();

        mCurrentUser = DbManager.getInstance().getCurrentUser();
        mLvIdeas = (ListView) findViewById(R.id.lvIdeas);
        mLvIdeas.setOnItemClickListener(this);

        if (mCurrentUser == null) {
            Intent accessIntent = new Intent(this, AccessActivity.class);
            startActivity(accessIntent);
        }
        else {
            mIdeas = DbManager.getInstance().getIdeas();
            checkUserRole();

            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mSearchResults = getResults(query);
            mIdeaAdapter = new IdeaAdapter(this, mSearchResults);
        } else {
            mIdeaAdapter = new IdeaAdapter(this, mIdeas);
        }

        mLvIdeas.setAdapter(mIdeaAdapter);
    }

    private List<Idea> getResults(String query) {

        ArrayList<Idea> queryResults = new ArrayList<Idea>();

        for (Idea idea: mIdeas) {
            if (idea.getName().toLowerCase().contains(query.toLowerCase())) {
                queryResults.add(idea);
            }
        }

        return queryResults;
    }

    private void checkUserRole() {
        if (mCurrentUser != null && mCurrentUser.getRole() == Role.ADMIN) {
            mIsAdmin = true;
        }
        else {
            mIsAdmin = false;
        }
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
        if (id == R.id.action_add) {
            addNewIdeaScreen();
            return true;
        } else if (id == R.id.action_logout) {
            logout();
            return true;
        } else if (id == R.id.search) {
            onSearchRequested();
            return true;
        } else if (id == R.id.clearSearch) {
            clearSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearSearch() {
        mIdeaAdapter = new IdeaAdapter(this, mIdeas);
        mLvIdeas.setAdapter(mIdeaAdapter);
    }

    private void logout() {
        DbManager.getInstance().setCurrentUser(null);

        Intent accessIntent = new Intent(this, AccessActivity.class);
        startActivity(accessIntent);
    }

    private void addNewIdeaScreen() {
        Intent addIdeaIntent = new Intent(this, AddIdeaActivity.class);
        startActivity(addIdeaIntent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Idea currentIdea = mIdeas.get(position);

        if (mIsAdmin) {
            goToEditActivity(currentIdea);
        }
        else {
            if (mIdeas.get(position).getOwner().getUsername().equals(mCurrentUser.getUsername())) {
                goToEditActivity(currentIdea);
            }
            else {
                Toast.makeText(this, "You are not the owner of this idea", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goToEditActivity(Idea idea) {
        Intent editIdeaIntent = new Intent(this, AddIdeaActivity.class);
        editIdeaIntent.putExtra("EDIT_IDEA", idea);
        startActivity(editIdeaIntent);
    }
}
