package com.tlnguyen.mentormatetest.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TL on 12/15/2014.
 */
public class User implements Parcelable {
    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.email);
        dest.writeInt(this.role == null ? -1 : this.role.ordinal());
    }

    public User() {
    }

    private User(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        int tmpRole = in.readInt();
        this.role = tmpRole == -1 ? null : Role.values()[tmpRole];
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
