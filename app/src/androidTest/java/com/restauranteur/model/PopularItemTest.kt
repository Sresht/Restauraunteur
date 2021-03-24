package com.restauranteur.model

import android.os.Parcel
import org.junit.Assert
import org.junit.Test

class PopularItemTest {
    @Test
    fun test_popular_item_is_parcelable() {
        val item = PopularItem("nachos", "https://www.nachos.com/img.jpg", 1000)
        val parcel = Parcel.obtain()
        item.writeToParcel(parcel, item.describeContents())
        parcel.setDataPosition(0)

        val createdItemFromParcel = PopularItem.CREATOR.createFromParcel(parcel)

        Assert.assertEquals(createdItemFromParcel.imageUrl, "https://www.nachos.com/img.jpg")
        Assert.assertEquals(createdItemFromParcel.name, "nachos")
        Assert.assertEquals(createdItemFromParcel.humanReadablePrice, "$10.00")
    }
}
