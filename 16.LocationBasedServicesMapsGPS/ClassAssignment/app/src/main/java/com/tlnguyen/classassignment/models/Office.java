package com.tlnguyen.classassignment.models;

/**
 * Created by TL on 1/22/2015.
 */
public class Office {
    private String name;
    private Double latitude;
    private Double longtitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }
}
