package com.restauranteur.view;

import com.restauranteur.model.Restaurant;

import java.util.ArrayList;

public interface RestaurantListObserver {
    void onRestaurantListChange(final ArrayList<Restaurant> newRestaurants);
}
