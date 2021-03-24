package com.restauranteur.model

import android.os.Parcel
import org.junit.Assert
import org.junit.Test

class StatusTest {
    @Test
    fun test_status_is_parcelable_not_unavailable() {
        val status = Status(null, intArrayOf(20, 30))
        val parcel = Parcel.obtain()
        status.writeToParcel(parcel, status.describeContents())
        parcel.setDataPosition(0)

        val createdStatusFromParcel = Status.CREATOR.createFromParcel(parcel)

        Assert.assertNull(createdStatusFromParcel.unavailableReason)
        Assert.assertEquals(createdStatusFromParcel.minutesAway, 20)
    }

    @Test
    fun test_status_is_parcelable_is_unavailable() {
        val status = Status("Closed", intArrayOf(20, 30))
        val parcel = Parcel.obtain()
        status.writeToParcel(parcel, status.describeContents())
        parcel.setDataPosition(0)

        val createdStatusFromParcel = Status.CREATOR.createFromParcel(parcel)

        Assert.assertNotNull(createdStatusFromParcel.unavailableReason)
        Assert.assertEquals(createdStatusFromParcel.unavailableReason, "Closed")
        Assert.assertEquals(createdStatusFromParcel.minutesAway, 20)
    }
}
