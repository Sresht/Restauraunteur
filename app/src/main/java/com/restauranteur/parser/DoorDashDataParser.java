package com.restauranteur.parser;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.restauranteur.model.DoorDashResponse;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static com.restauranteur.constant.DoordashApiConstants.DOORDASH_API_BASE_URL;
import static com.restauranteur.constant.DoordashApiConstants.DOORDASH_API_GET_REQUEST_PARAMS;
import static com.restauranteur.constant.DoordashApiConstants.DOORDASH_API_RESTAURANTS_LIMIT;
import static com.restauranteur.constant.DoordashApiConstants.DOORDASH_API_SEARCH_COORDINATES;

public class DoorDashDataParser {
    public static DoorDashDataService getDoorDashData(final int pageNumber) {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(DoorDashResponse.class, new DoorDashDataDeserializer())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getDoorDashApiUrl(pageNumber))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(DoorDashDataService.class);
    }

    private static class DoorDashDataDeserializer implements JsonDeserializer<DoorDashResponse> {
        @Override
        public DoorDashResponse deserialize(
                final JsonElement json,
                final Type typeOfT,
                final JsonDeserializationContext context) {
            return new Gson().fromJson(json, DoorDashResponse.class);
        }
    }

    private static String getDoorDashApiUrl(final int pageNumber) {
        return String.format(
                DOORDASH_API_BASE_URL,
                DOORDASH_API_SEARCH_COORDINATES.first,
                DOORDASH_API_SEARCH_COORDINATES.second,
                pageNumber * DOORDASH_API_RESTAURANTS_LIMIT,
                DOORDASH_API_RESTAURANTS_LIMIT);
    }

    public interface DoorDashDataService {
        @GET(DOORDASH_API_GET_REQUEST_PARAMS)
        Observable<DoorDashResponse> getRestaurants();
    }
}
