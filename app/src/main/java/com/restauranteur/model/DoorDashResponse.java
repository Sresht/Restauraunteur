package com.restauranteur.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DoorDashResponse implements Parcelable {
    // TODO handle error codes

    private final int numResults;
    private final int nextOffSet;
    private final ArrayList<Restaurant> stores;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(numResults);
        parcel.writeInt(nextOffSet);
        parcel.writeList(stores);
    }

    public ArrayList<Restaurant> getStores() {
        return this.stores;
    }

    protected DoorDashResponse(final Parcel input) {
        numResults = input.readInt();
        nextOffSet = input.readInt();
        stores = input.createTypedArrayList(Restaurant.CREATOR);
    }

    public static final Parcelable.Creator<DoorDashResponse> CREATOR = new Parcelable.Creator<DoorDashResponse>() {
        @Override
        public DoorDashResponse createFromParcel(final Parcel source) {
            return new DoorDashResponse(source);
        }

        @Override
        public DoorDashResponse[] newArray(final int size) {
            return new DoorDashResponse[size];
        }
    };
}
