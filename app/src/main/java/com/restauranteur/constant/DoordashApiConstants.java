package com.restauranteur.constant;

import android.util.Pair;

public class DoordashApiConstants {
    public static final String DOORDASH_API_BASE_URL = "https://api.doordash.com/v1/store_feed/?lat=%s&lng=%s&offset=%s&limit=%s";
    public static final int DOORDASH_API_RESTAURANTS_LIMIT = 15;
    public static final Pair<String, String> DOORDASH_API_SEARCH_COORDINATES = new Pair<>("37.422740", "-122.139956");
    public static final String DOORDASH_API_DESCRIPTION_DELIMITER = ",";
    public static final int DOORDASH_API_RESTAURANT_ASAP_MINUTES_RANGE_LENGTH = 2;
    public static final String DOORDASH_API_GET_REQUEST_PARAMS = " ";
}
