package com.restauranteur.model

import android.os.Parcel
import org.junit.Assert
import org.junit.Test

class MenuTest {
    @Test
    fun test_menu_is_parcelable() {
        // TODO this test isn't working because of a weird parcel parsing error related to PopularItem

        val firstItem = PopularItem("nachos", "https://www.nachos.com/img.jpg", 1000)
        val secondItem = PopularItem("pizza", "https://www.pizza.com/img.jpg", 500)
        val menu = Menu(arrayListOf(firstItem, secondItem))

        val parcel = Parcel.obtain()
        menu.writeToParcel(parcel, menu.describeContents())
        parcel.setDataPosition(0)

        val createdItemFromParcel = Menu.CREATOR.createFromParcel(parcel)

        Assert.assertEquals(createdItemFromParcel.menus.get(0).humanReadablePrice, "$10.00")
        Assert.assertEquals(createdItemFromParcel.menus.get(0).name, "nachos")
        Assert.assertEquals(createdItemFromParcel.menus.get(0).imageUrl, "https://www.nachos.com/img.jpg")

        Assert.assertEquals(createdItemFromParcel.menus.get(1).humanReadablePrice, "$5.00")
        Assert.assertEquals(createdItemFromParcel.menus.get(1).name, "pizza")
        Assert.assertEquals(createdItemFromParcel.menus.get(1).imageUrl, "https://www.pizza.com/img.jpg")

    }
}
