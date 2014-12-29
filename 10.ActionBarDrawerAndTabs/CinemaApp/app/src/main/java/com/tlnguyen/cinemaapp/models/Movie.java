package com.tlnguyen.cinemaapp.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

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

    public String getCast() {
        return getString("cast");
    }

    public void setCast(String value) {
        put("cast", value);
    }
}
