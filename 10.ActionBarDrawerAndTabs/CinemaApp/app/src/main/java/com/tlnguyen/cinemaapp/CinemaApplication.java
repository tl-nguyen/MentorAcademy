package com.tlnguyen.cinemaapp;

import android.app.Application;

import com.parse.Parse;

public class CinemaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "9JHLgP5rga9E64jyQLCeZD18DoogqxXcraDLMOec", "MkKfPHRoftHjnTZEiOl3TGz9Nku5skexk7ZC9660");
    }
}
