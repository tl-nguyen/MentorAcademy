package com.tlnguyen.weatherspot.data;

import android.net.Uri;

public interface Constants {

    // Constants for the content provider
    public static final String AUTHORITY = "com.tlnguyen.weatherspot.data.SpotProvider";
    public static final String DATABASE_NAME = "WeatherSpot.db";
    public static final String SPOT_TABLE = "Spots";
    public static final String URL = "content://" + AUTHORITY + "/" + SPOT_TABLE;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // Constants for the database
    public static final String COL_ID = "id";
    public static final String COL_PHOTO_PATH = "photo";
    public static final String COL_WEATHER = "description";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_CREATED_AT = "createdAt";

    // Constants used in content URI
    public static final int SPOTS = 1;
    public static final int SPOT_ID = 2;
}
