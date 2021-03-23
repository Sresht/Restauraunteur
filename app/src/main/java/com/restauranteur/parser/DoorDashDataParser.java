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

public class DoorDashDataParser {
    private static final String API_URL = "https://api.doordash.com/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50";

    public static DoorDashDataService getDoorDashData() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DoorDashResponse.class, new DoorDashDataDeserializer())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
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

    public interface DoorDashDataService {
        @GET(" ")
        Observable<DoorDashResponse> getRestaurants();
    }
}
