package com.tlnguyen.homeowork_gamebook.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.tlnguyen.homeowork_gamebook.common.Name;

/**
 * Created by TL on 11/30/2014.
 */
public class Hero implements Parcelable {
    private Name name;
    private int blood;
    private int energy;

    public Hero(Name name, int blood, int energy) {
        this.name = name;
        this.blood = blood;
        this.energy = energy;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.name == null ? -1 : this.name.ordinal());
        dest.writeInt(this.blood);
        dest.writeInt(this.energy);
    }

    private Hero(Parcel in) {
        int tmpName = in.readInt();
        this.name = tmpName == -1 ? null : Name.values()[tmpName];
        this.blood = in.readInt();
        this.energy = in.readInt();
    }

    public static final Parcelable.Creator<Hero> CREATOR = new Parcelable.Creator<Hero>() {
        public Hero createFromParcel(Parcel source) {
            return new Hero(source);
        }

        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };
}
