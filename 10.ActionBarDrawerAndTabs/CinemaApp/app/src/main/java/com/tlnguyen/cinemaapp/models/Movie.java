package com.tlnguyen.cinemaapp.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Movie")
public class Movie extends ParseObject {

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String value) {
        put("title", value);
    }

    public ParseFile getPicture() {
        return getParseFile("picture");
    }

    public void setPicture(ParseFile value) {
        put("picture", value);
    }

    public List<Cinema> getCinemas() {
        return getList("cinemas");
    }

    public void setCinemas(List<Cinema> value) {
        put("cinemas", value);
    }
}
