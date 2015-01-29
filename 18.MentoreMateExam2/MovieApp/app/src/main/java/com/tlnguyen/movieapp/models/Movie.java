package com.tlnguyen.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tl on 29.01.15.
 */
public class Movie implements Parcelable {
    private int id;
    private String name;
    private String description;

    public Movie() {
    }

    public Movie(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Movie(int id, String name, String description) {
        this(name, description);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
