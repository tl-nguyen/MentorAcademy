package com.tlnguyen.movieapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class MovieProvider extends ContentProvider {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    // projection map for a query
    private static HashMap<String, String> MovieMap;

    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Constants.AUTHORITY, Constants.MOVIE_TABLE, Constants.MOVIES);
        uriMatcher.addURI(Constants.AUTHORITY, Constants.MOVIE_TABLE + "/#", Constants.MOVIE_ID);
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        dbHelper = new DBHelper(context);
        // permissions to be writable
        database = dbHelper.getWritableDatabase();

        if(database == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DBHelper.getTableName());

        switch (uriMatcher.match(uri)) {
            // maps all database column names
            case Constants.MOVIES:
                queryBuilder.setProjectionMap(MovieMap);
                break;
            case Constants.MOVIE_ID:
                queryBuilder.appendWhere( Constants.COL_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == ""){
            // No sorting-> sort on names by default
            sortOrder = Constants.COL_ID + " DESC";
        }

        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        // Register to watch a content URI for changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long row = database.insert(DBHelper.getTableName(), "", values);

        // If record is added successfully
        if(row > 0) {
            Uri newUri = ContentUris.withAppendedId(Constants.CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int count = 0;

        switch (uriMatcher.match(uri)){
            case Constants.MOVIES:
                // delete all the records of the table
                count = database.delete(DBHelper.getTableName(), selection, selectionArgs);
                break;
            case Constants.MOVIE_ID:
                String id = uri.getLastPathSegment();	//gets the id
                count = database.delete( DBHelper.getTableName(), Constants.COL_ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int count = 0;

        switch (uriMatcher.match(uri)){
            case Constants.MOVIES:
                count = database.update(DBHelper.getTableName(), values, selection, selectionArgs);
                break;
            case Constants.MOVIE_ID:
                count = database.update(DBHelper.getTableName(), values,
                        Constants.COL_ID + " = " + uri.getLastPathSegment()
                                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
