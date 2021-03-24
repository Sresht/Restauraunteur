package com.restauranteur.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.TestOnly;

import java.text.NumberFormat;
import java.util.Locale;

public class PopularItem implements Parcelable {
    private final String name;
    private final String img_url;
    private final int price;

    @Override
    public int describeContents() {
        return 0;
    }

    protected PopularItem(final Parcel in) {
        name = in.readString();
        img_url = in.readString();
        price = in.readInt();
    }

    @TestOnly
    public PopularItem(final String name, final String img_url, final int price) {
        this.name = name;
        this.img_url = img_url;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return img_url;
    }

    public String getHumanReadablePrice() {
        final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return numberFormat.format(price / 100.0);
    }

    @Override
    public void writeToParcel(final Parcel dest, final int i) {
        dest.writeString(name);
        dest.writeString(img_url);
        dest.writeInt(price);
    }

    public static final Parcelable.Creator<PopularItem> CREATOR = new Parcelable.Creator<PopularItem>() {
        @Override
        public PopularItem createFromParcel(final Parcel source) {
            return new PopularItem(source);
        }

        @Override
        public PopularItem[] newArray(final int size) {
            return new PopularItem[size];
        }
    };
}
