package com.restauranteur.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.facebook.soloader.SoLoader;
import com.restauranteur.R;
import com.restauranteur.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoLoader.init(this, false);

        fetchRestaurants();
    }

    private void fetchRestaurants() {
        displaySpinnerFragment();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) { }

        // TODO replace with network logic
        final ArrayList<Restaurant> restaurantsList = new ArrayList<>();
        final ArrayList<String> dishes = new ArrayList<>();
        dishes.add("pasta");
        for (int i = 0; i < 1000; i++) {
            restaurantsList.add(new Restaurant(Integer.toString(i), dishes));
        }

        if (restaurantsList != null) {
            displayRestaurants(restaurantsList);
        }
    }

    private void displaySpinnerFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new LoadingFragment())
                .commit();
    }

    private void displayRestaurants(final ArrayList<Restaurant> restaurants) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, MainFragment.newInstance(restaurants))
                .commit();
    }
}
