package com.tlnguyen.cinemaapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.tlnguyen.cinemaapp.models.Cinema;
import com.tlnguyen.cinemaapp.models.Movie;

public class CinemaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        // Register Parse Subclasses
        ParseObject.registerSubclass(Movie.class);
        ParseObject.registerSubclass(Cinema.class);

        Parse.initialize(this, "9JHLgP5rga9E64jyQLCeZD18DoogqxXcraDLMOec", "MkKfPHRoftHjnTZEiOl3TGz9Nku5skexk7ZC9660");

//        DataSeed.seed();
    }
}
