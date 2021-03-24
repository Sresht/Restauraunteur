package com.restauranteur.view;

import com.restauranteur.model.PopularItem;

import java.util.ArrayList;

public interface RestaurantOnClickHandler {
    void onRestaurantClicked(final ArrayList<PopularItem> menuItems);
}
