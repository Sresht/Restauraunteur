package com.restauranteur.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.TestOnly;

import static com.restauranteur.constant.DoordashApiConstants.DOORDASH_API_RESTAURANT_ASAP_MINUTES_RANGE_LENGTH;

public class Status implements Parcelable {
    private final String unavailable_reason;
    private int[] asap_minutes_range = new int[DOORDASH_API_RESTAURANT_ASAP_MINUTES_RANGE_LENGTH];

    @Override
    public int describeContents() {
        return 0;
    }

    protected Status(final Parcel in) {
        unavailable_reason = in.readString();
        in.readIntArray(asap_minutes_range);
    }

    @TestOnly
    public Status(final String unavailable_reason, final int[] asap_minutes_range) {
        this.unavailable_reason = unavailable_reason;
        this.asap_minutes_range = asap_minutes_range;
    }

    public String getUnavailableReason() {
        return unavailable_reason;
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
