package com.tlnguyen.classdemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    public static final String PREFS = "SETTING";
    public static final String SAVED_USERNAME = "SAVED_USERNAME";

    private String username = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.contains(SAVED_USERNAME)) {
            username = sharedPreferences.getString(SAVED_USERNAME, "guest");
        }
        username = "happyUser";

        editor.putString(SAVED_USERNAME, username);
        editor.commit();
    }

    public void addBirthday(View view) {
        EditText etBirthday = (EditText) findViewById(R.id.etBirthday);
        EditText etName = (EditText) findViewById(R.id.etName);

        String date = etBirthday.getText().toString();
        String name = etName.getText().toString();

        ContentValues values = new ContentValues();
        values.put("Birthday", date);
        values.put("Name", name);

        Uri uri = getContentResolver().insert(Uri.parse("content://com.tlnguyen.classdemo.CustomProvider/BirthdayTable"), values);
    }

    public void selectBirthday(View view) {

        Cursor cursor = getContentResolver().query(Uri.parse("content://com.tlnguyen.classdemo.CustomProvider/BirthdayTable"),
                null, null, null, null);

        if (!cursor.moveToFirst()) {
            Log.d("DB_SELECT", "EMpty db");
        }
        else {
            do {
                Log.d("DB_SELECT", cursor.getString(cursor.getColumnIndex("Name")));
            }
            while (cursor.moveToNext());
        }


    }

    public void deleteBirthday(View view) {

        int count = getContentResolver().delete(Uri.parse("content://com.tlnguyen.classdemo.CustomProvider/BirthdayTable"), null, null);

        Log.d("DB_DELETE", count+"");

    }
}
