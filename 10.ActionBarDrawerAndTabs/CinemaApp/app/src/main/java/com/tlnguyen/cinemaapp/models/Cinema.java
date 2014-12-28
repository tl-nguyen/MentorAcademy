package com.tlnguyen.cinemaapp.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Cinema")
public class Cinema extends ParseObject{

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String value) {
        put("title", value);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String value) {
        put("address", value);
    }

    public String getWorkingTime() {
        return getString("workingTime");
    }

    public void setWorkingTime(String value) {
        put("workingTime", value);
    }

    public List<ParseFile> getPhotos() {
        return getList("photos");
    }

    public void setPhotos(List<ParseFile> value) {
        put("photos", value);
    }

    public List<Movie> getMovies() {
        return getList("movies");
    }

    public void setMovies(List<Movie> value) {
        put("movies", value);
    }
}
