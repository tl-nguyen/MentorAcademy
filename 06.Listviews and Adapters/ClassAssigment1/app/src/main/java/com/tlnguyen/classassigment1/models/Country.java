package com.tlnguyen.classassigment1.models;

/**
 * Created by TL on 12/4/2014.
 */
public class Country {
    private String name;
    private int year;

    public Country(String name, int year) {
        this.name = name;
        this.year = year;
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
