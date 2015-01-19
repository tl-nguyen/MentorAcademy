package com.mentormate.academy.lecture14;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Stefan.Doychev on 13.01.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "birthTable";
    private static final int DATABASE_VERSION = 3;
    private static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " name TEXT NOT NULL, " +
            " birthday TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, DATABASE_VERSION);
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