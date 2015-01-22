package com.tlnguyen.classassignment.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "Offices";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " " + Constants.COL_NAME + " TEXT NOT NULL, " +
            " " + Constants.COL_LAT + " TEXT NOT NULL, " +
            " "+ Constants.COL_LONG +" TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
                + ". Old data will be destroyed");
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

}