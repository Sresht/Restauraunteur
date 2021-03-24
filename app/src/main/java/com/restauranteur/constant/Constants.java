package com.restauranteur.constant;

import android.util.Pair;

public class Constants {
    public static final String DOORDASH_API_BASE_URL = "https://api.doordash.com/v1/store_feed/?lat=%s&lng=%s&offset=%s&limit=%s";
    public static final int DOORDASH_API_RESTAURANTS_LIMIT = 50;
    public static final Pair<String, String> SEARCH_COORDINATES = new Pair<>("37.422740", "-122.139956");
}
