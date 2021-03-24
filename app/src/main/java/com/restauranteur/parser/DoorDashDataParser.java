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

import static com.restauranteur.constant.Constants.DOORDASH_API_BASE_URL;
import static com.restauranteur.constant.Constants.DOORDASH_API_RESTAURANTS_LIMIT;
import static com.restauranteur.constant.Constants.SEARCH_COORDINATES;

public class DoorDashDataParser {
    private static final int offset = 0;

    public static DoorDashDataService getDoorDashData() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DoorDashResponse.class, new DoorDashDataDeserializer())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getDoorDashApiUrl())
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

    private static String getDoorDashApiUrl() {
        return String.format(
                DOORDASH_API_BASE_URL,
                SEARCH_COORDINATES.first,
                SEARCH_COORDINATES.second,
                offset,
                DOORDASH_API_RESTAURANTS_LIMIT);
    }

    public interface DoorDashDataService {
        @GET(" ")
        Observable<DoorDashResponse> getRestaurants();
    }
}
