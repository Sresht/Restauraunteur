package com.restauranteur.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Status implements Parcelable {
    private final String unavailable_reason;
    private final int[] asap_minutes_range = new int[]{2};

    @Override
    public int describeContents() {
        return 0;
    }

    protected Status(final Parcel in) {
        unavailable_reason = in.readString();
        in.readIntArray(asap_minutes_range);
    }

    public boolean getIsAvailable() {
        return unavailable_reason != null;
    }

    public int getMinutesAway() {
        return asap_minutes_range[0];
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(unavailable_reason);
        dest.writeIntArray(asap_minutes_range);
    }

    public static final Parcelable.Creator<Status> CREATOR = new Parcelable.Creator<Status>() {
        @Override
        public Status createFromParcel(final Parcel source) {
            return new Status(source);
        }

        @Override
        public Status[] newArray(final int size) {
            return new Status[size];
        }
    };
}
