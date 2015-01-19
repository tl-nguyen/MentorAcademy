package com.mentormate.academy.lecture14;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteAllBirthdays (View view) {
        // Delete all the records and the table of the database provider
        int count = getContentResolver().delete(Constants.CONTENT_URI, null, null);
        Toast.makeText(this, count + " records are deleted.", Toast.LENGTH_LONG).show();
    }

    public void addBirthday(View view) {
        // Add a new birthday record
        ContentValues values = new ContentValues();
        values.put(Constants.DB_NAME, ((EditText)findViewById(R.id.name)).getText().toString());
        values.put(Constants.DB_BIRTHDAY, ((EditText)findViewById(R.id.birthday)).getText().toString());
        Uri uri = getContentResolver().insert(Constants.CONTENT_URI, values);
        Toast.makeText(this, uri.toString() + " inserted!", Toast.LENGTH_LONG).show();
    }

    public void showAllBirthdays(View view) {
        // Show all the birthdays sorted by friend's name
        Cursor c = getContentResolver().query(Constants.CONTENT_URI, null, null, null, "name");
        String result = "Results:";
        if (!c.moveToFirst()) {
            Toast.makeText(this, result+" no content yet!", Toast.LENGTH_LONG).show();
        } else {
            do{
                result = result + "\n" + c.getString(c.getColumnIndex(Constants.DB_NAME)) +
                        " with id " +  c.getString(c.getColumnIndex(Constants.DB_ID)) +
                        " has birthday: " + c.getString(c.getColumnIndex(Constants.DB_BIRTHDAY));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }

    public void exportDatabase(View view) {
        Util.exportDB();
    }

    public void importDatabase(View view) {
        Util.importDB();
    }

    public void showList(View view) {

        Intent i = new Intent(this, ListViewActivity.class);
        startActivity(i);
    }
}
