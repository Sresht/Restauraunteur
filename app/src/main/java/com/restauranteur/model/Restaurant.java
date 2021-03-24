package com.restauranteur.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;

import static com.restauranteur.constant.DoordashApiConstants.DOORDASH_API_DESCRIPTION_DELIMITER;

public class Restaurant implements Parcelable {
    private final String name;
    private final String url;
    private final String cover_img_url;
    private final ArrayList<Menu> menus;
    private final Status status;
    private final String description;

    @Override
    public int describeContents() {
        return 0;
    }

    protected Restaurant(final Parcel in) {
        name = in.readString();
        url = in.readString();
        cover_img_url = in.readString();
        menus = in.createTypedArrayList(Menu.CREATOR);
        status = in.readTypedObject(Status.CREATOR);
        description = in.readString();
    }

    @TestOnly
    public Restaurant(
            final String name,
            final String url,
            final String cover_img_url,
            final ArrayList<Menu> menus,
            final Status status,
            final String description) {
        this.name = name;
        this.url = url;
        this.cover_img_url = cover_img_url;
        this.menus = menus;
        this.status = status;
        this.description = description;
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

    public String getCuisine() {
        // TODO this isn't perfect, but it works most of the time. Might need to hit a different
        // endpoint
        return description.split(DOORDASH_API_DESCRIPTION_DELIMITER)[0];
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(cover_img_url);
        dest.writeTypedList(menus);
        dest.writeTypedObject(status, i);
        dest.writeString(description);
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
