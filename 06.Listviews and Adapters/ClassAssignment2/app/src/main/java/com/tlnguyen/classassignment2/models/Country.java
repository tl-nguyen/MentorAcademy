package com.tlnguyen.classassignment2.models;

/**
 * Created by TL on 12/4/2014.
 */
public class Country {
    private String name;
    private int year;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private int image;

    public Country(String name, int year, int image) {
        this.name = name;
        this.year = year;
        this.image = image;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
