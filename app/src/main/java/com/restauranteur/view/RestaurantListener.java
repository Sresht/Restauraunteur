package com.restauranteur.view;

import com.restauranteur.model.PopularItem;

import java.util.ArrayList;

public interface RestaurantListener {
    void onRestaurantClicked(ArrayList<PopularItem> menuItems);
}
