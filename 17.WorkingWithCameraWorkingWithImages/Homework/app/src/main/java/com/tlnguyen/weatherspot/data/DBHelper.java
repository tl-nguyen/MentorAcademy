package com.tlnguyen.weatherspot.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_TABLE = "CREATE TABLE " + Constants.SPOT_TABLE +
            " (" + Constants.COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Constants.COL_LATITUDE + " TEXT NOT NULL, " +
            Constants.COL_LONGITUDE + " TEXT NOT NULL, " +
            Constants.COL_CREATED_AT + " DATE NOT NULL, " +
            Constants.COL_PHOTO_PATH + " TEXT, " +
            Constants.COL_WEATHER +" TEXT NOT NULL);";

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
        db.execSQL("DROP TABLE IF EXISTS " +  Constants.SPOT_TABLE);
        onCreate(db);
    }

    public static String getTableName() {
        return Constants.SPOT_TABLE;
    }

}