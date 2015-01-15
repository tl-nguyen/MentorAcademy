package com.tlnguyen.assignment.data;

import android.net.Uri;

/**
 * Created by Stefan.Doychev on 13.01.2015.
 */
public interface Constants {

    // Constants for the content provider
    public static final String AUTHORITY = "com.tlnguyen.assignment.data.WeatherProvider";
    public static final String DATABASE_NAME = "Weather.db";
    public static final String URL = "content://" + AUTHORITY + "/" + DATABASE_NAME;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // Constants for the database
    public static final String COL_ID = "id";
    public static final String COL_TEMP = "temperature";
    public static final String COL_PRESSURE = "pressure";
    public static final String COL_HUMIDITY = "humidity";
    public static final String COL_DESCRIPTION = "description";

    // Constants used in content URI
    public static final int FRIENDS = 1;
    public static final int FRIENDS_ID = 2;
}
