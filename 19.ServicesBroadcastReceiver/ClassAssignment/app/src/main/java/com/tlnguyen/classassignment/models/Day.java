package com.tlnguyen.classassignment.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TL on 1/12/2015.
 */
public class Day implements Parcelable {
    private String temp;
    private String pressure;
    private String humidity;
    private String description;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
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
        dest.writeString(this.temp);
        dest.writeString(this.pressure);
        dest.writeString(this.humidity);
        dest.writeString(this.description);
    }

    public Day() {
    }

    private Day(Parcel in) {
        this.temp = in.readString();
        this.pressure = in.readString();
        this.humidity = in.readString();
        this.description = in.readString();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}
