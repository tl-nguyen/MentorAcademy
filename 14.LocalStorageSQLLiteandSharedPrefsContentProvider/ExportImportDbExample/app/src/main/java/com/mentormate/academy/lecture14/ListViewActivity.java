package com.mentormate.academy.lecture14;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ListViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //Generate ListView from SQLite Database
        displayListView();
    }

    private void displayListView() {

        Cursor cursor = getContentResolver().query(Constants.CONTENT_URI, null, null, null, "name");

        String[] columns = new String[] {
                Constants.DB_NAME,
                Constants.DB_BIRTHDAY
        };

        int[] to = new int[] {
                android.R.id.text1,
                android.R.id.text2
        };

        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_2,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(dataAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
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
}
