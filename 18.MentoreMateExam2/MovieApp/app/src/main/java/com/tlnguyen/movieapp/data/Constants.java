package com.tlnguyen.movieapp.data;

import android.net.Uri;

public interface Constants {

    // Constants for the content provider
    public static final String AUTHORITY = "com.tlnguyen.movieapp.data.MovieProvider";
    public static final String DATABASE_NAME = "MovieApp.db";
    public static final String MOVIE_TABLE = "Movies";
    public static final String URL = "content://" + AUTHORITY + "/" + MOVIE_TABLE;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // Constants for the database
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";

    // Constants used in content URI
    public static final int MOVIES = 1;
    public static final int MOVIE_ID = 2;
}
