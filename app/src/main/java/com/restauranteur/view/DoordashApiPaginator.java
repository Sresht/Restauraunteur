package com.restauranteur.view;

import com.restauranteur.model.Restaurant;

import java.util.ArrayList;

public interface DoordashApiPaginator {
    ArrayList<Restaurant> getNextPage();

    int getOffset();
}
