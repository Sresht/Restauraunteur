package com.restauranteur.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Parcelable {
    private final String name;
    private final List<String> dishes;

    @Override
    public int describeContents() {
        return 0;
    }

    public Restaurant(final String name, final ArrayList<String> dishes) {
        this.name = name;
        this.dishes = dishes;
    }

    protected Restaurant(final Parcel in) {
        this.name = in.readString();
        this.dishes = in.readArrayList(Restaurant.class.getClassLoader());
    }

    public String getName() {
        return this.name;
    }

    public List<String> getDishes() {
        return this.dishes;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeList(this.dishes);
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

}
