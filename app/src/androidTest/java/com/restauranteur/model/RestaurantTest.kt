package com.restauranteur.model

import android.os.Parcel
import org.junit.Assert
import org.junit.Test

class RestaurantTest {

    // TODO this test isn't working because of a weird parcel parsing error related to Status
    @Test
    fun test_restaurant_is_parcelable() {
        val firstPopularItem = PopularItem("nachos", "https://nachos.com/img.jpg", 1000)
        val secondPopularItem = PopularItem("fries", "https://fries.com/img.jpg", 500)
        val menu = Menu(arrayListOf(firstPopularItem, secondPopularItem))
        val status = Status(null, intArrayOf(20, 20))

        val restaurant = Restaurant(
                "Pizza Place", "https://pizzaplace.com", "https://pizzaplace.com/img.jpg", arrayListOf(menu), status, "Pizza, Love, Family")

        val parcel = Parcel.obtain()
        restaurant.writeToParcel(parcel, restaurant.describeContents())

        parcel.setDataPosition(0)

        val createdRestaurantFromParcel = Restaurant.CREATOR.createFromParcel(parcel)

        Assert.assertEquals(createdRestaurantFromParcel.coverImageUrl, "https://pizzaplace.com/img.jpg")
        Assert.assertEquals(createdRestaurantFromParcel.cuisine, "Pizza")
        Assert.assertEquals(createdRestaurantFromParcel.name, "Pizza Place")
        Assert.assertEquals(createdRestaurantFromParcel.status.minutesAway, 20)
    }
}
