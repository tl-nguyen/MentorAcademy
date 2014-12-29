package com.tlnguyen.cinemaapp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("MovieCinema")
public class MovieCinema extends ParseObject{

    public Cinema getCinema() {
        return (Cinema) getParseObject("cinema");
    }

    public void setCinema(Cinema value) {
        put("cinema" , value);
    }

    public Movie getMovie() {
        return (Movie) getParseObject("movie");
    }

    public void setMovie(Movie value) {
        put("movie", value);
    }

    public int getAvailableSeats() {
        return getInt("availableSeats");
    }

    public void setAvailableSeats(int value) {
        put("availableSeats", value);
    }
}
