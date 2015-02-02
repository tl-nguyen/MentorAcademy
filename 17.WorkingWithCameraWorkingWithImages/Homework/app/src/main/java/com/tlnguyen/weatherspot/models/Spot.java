package com.tlnguyen.weatherspot.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by tl on 01.02.15.
 */
public class Spot implements Parcelable {
    private int id;
    private LatLng location;
    private String weather;
    private String photoPath;
    private Date createdAt;

    public Spot() {
    }

    public Spot(LatLng location, String weather, String photoPath, Date createdAt) {
        this.location = location;
        this.weather = weather;
        this.photoPath = photoPath;
        this.createdAt = createdAt;
    }

    public Spot(int id, LatLng location, String weather, String photoPath, Date createdAt) {
        this(location, weather, photoPath, createdAt);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
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
        dest.writeParcelable(this.location, flags);
        dest.writeString(this.weather);
        dest.writeString(this.photoPath);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
    }

    private Spot(Parcel in) {
        this.id = in.readInt();
        this.location = in.readParcelable(LatLng.class.getClassLoader());
        this.weather = in.readString();
        this.photoPath = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
    }

    public static final Creator<Spot> CREATOR = new Creator<Spot>() {
        public Spot createFromParcel(Parcel source) {
            return new Spot(source);
        }

        public Spot[] newArray(int size) {
            return new Spot[size];
        }
    };
}
