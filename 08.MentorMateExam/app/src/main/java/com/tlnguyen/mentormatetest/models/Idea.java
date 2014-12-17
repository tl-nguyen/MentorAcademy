package com.tlnguyen.mentormatetest.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TL on 12/15/2014.
 */
public class Idea implements Parcelable {
    private String name;
    private String description;
    private User owner;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Idea(String name, String description, User owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeParcelable(this.owner, 0);
    }

    private Idea(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.owner = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Idea> CREATOR = new Parcelable.Creator<Idea>() {
        public Idea createFromParcel(Parcel source) {
            return new Idea(source);
        }

        public Idea[] newArray(int size) {
            return new Idea[size];
        }
    };
}
