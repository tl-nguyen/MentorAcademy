package com.mentormate.academy.lecture14;

import android.net.Uri;

/**
 * Created by Stefan.Doychev on 13.01.2015.
 */
public interface Constants {

    // Constants for the content provider
    public static final String AUTHORITY            = "com.mentormate.academy.lecture14.BirthProvider";
    public static final String DATABASE_NAME        = "Birthday.db";
    public static final String URL                  = "content://" + AUTHORITY + "/" + DATABASE_NAME;
    public static final Uri CONTENT_URI             = Uri.parse(URL);

    public static String PATH_TO_APP_FOLDER         = "/data/com.mentormate.academy.lecture14/";
    public static String LOCAL_DATABASE             = "databases/name";
    public static String EXPORT_DATABASE            = "Export/" + DATABASE_NAME;
    public static String IMPORT_DATABASE            = "Import/" + DATABASE_NAME;

    // Constants for the database
    public static final String DB_ID                = "_id";
    public static final String DB_NAME              = "name";
    public static final String DB_BIRTHDAY          = "birthday";

    // Constants used in content URI
    public static final int FRIENDS                 = 1;
    public static final int FRIENDS_ID              = 2;
}
