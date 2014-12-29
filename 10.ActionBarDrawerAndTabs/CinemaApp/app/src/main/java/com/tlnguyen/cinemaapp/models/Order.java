package com.tlnguyen.cinemaapp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Order")
public class Order extends ParseObject {

    public ParseUser getCreatedBy() {
        return getParseUser("createdBy");
    }

    public void setCreatedBy(ParseUser value) {
        put("createdBy", value);
    }

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

    public int getQuantity() {
        return getInt("quantity");
    }

    public void setQuantity(int value) {
        put("quantity", value);
    }
}
