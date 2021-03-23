package com.restauranteur.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Menu implements Parcelable {
    private final ArrayList<PopularItem> popular_items;

    @Override
    public int describeContents() {
        return 0;
    }

    protected Menu(final Parcel in) {
        popular_items = in.createTypedArrayList(PopularItem.CREATOR);
    }


    public ArrayList<PopularItem> getMenus() {
        return popular_items;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeList(popular_items);
    }

    public static final Parcelable.Creator<Menu> CREATOR = new Parcelable.Creator<Menu>() {
        @Override
        public Menu createFromParcel(final Parcel source) {
            return new Menu(source);
        }

        @Override
        public Menu[] newArray(final int size) {
            return new Menu[size];
        }
    };
}