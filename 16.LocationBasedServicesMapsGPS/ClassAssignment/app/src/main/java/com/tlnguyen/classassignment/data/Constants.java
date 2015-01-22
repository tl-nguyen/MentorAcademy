package com.tlnguyen.classassignment.data;

import android.net.Uri;

public interface Constants {

    // Constants for the content provider
    public static final String AUTHORITY = "com.tlnguyen.classassignment.data.OfficeProvider";
    public static final String DATABASE_NAME = "Offices.db";
    public static final String URL = "content://" + AUTHORITY + "/" + DATABASE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // Constants for the database
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_LAT = "lat";
    public static final String COL_LONG = "long";

    // Constants used in content URI
    public static final int FRIENDS = 1;
    public static final int FRIENDS_ID = 2;
}
