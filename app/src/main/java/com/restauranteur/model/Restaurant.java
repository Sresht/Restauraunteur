package com.restauranteur.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Restaurant implements Parcelable {
    private final String name;
    private final String url;
    private final String cover_img_url;
    private final ArrayList<Menu> menus;

    @Override
    public int describeContents() {
        return 0;
    }

    protected Restaurant(final Parcel in) {
        name = in.readString();
        url = in.readString();
        cover_img_url = in.readString();
        menus = in.createTypedArrayList(Menu.CREATOR);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCoverImageUrl() {
        return cover_img_url;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(cover_img_url);
        dest.writeTypedList(menus);
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(final Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(final int size) {
            return new Restaurant[size];
        }
    };
}
