package com.tlnguyen.weatherspot.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by tl on 01.02.15.
 */
public class Spot implements Parcelable {
    private int id;
    private double latitude;
    private double longitude;
    private String weather;
    private String photoPath;
    private Date createdAt;

    public Spot() {
    }

    public Spot(double latitude, double longitude, String weather, String photoPath, Date createdAt) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.weather = weather;
        this.photoPath = photoPath;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.weather);
        dest.writeString(this.photoPath);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
    }

    private Spot(Parcel in) {
        this.id = in.readInt();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.weather = in.readString();
        this.photoPath = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
    }

    public static final Parcelable.Creator<Spot> CREATOR = new Parcelable.Creator<Spot>() {
        public Spot createFromParcel(Parcel source) {
            return new Spot(source);
        }

        public Spot[] newArray(int size) {
            return new Spot[size];
        }
    };
}
