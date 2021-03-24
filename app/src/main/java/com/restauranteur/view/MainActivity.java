package com.restauranteur.view;

import android.os.Bundle;

import com.facebook.soloader.SoLoader;
import com.restauranteur.R;
import com.restauranteur.model.DoorDashResponse;
import com.restauranteur.model.PopularItem;
import com.restauranteur.model.Restaurant;
import com.restauranteur.parser.DoorDashDataParser;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity
        implements RestaurantOnClickHandler, RestaurantListObserver {
    private static final String BACK_STACK_MAIN_FRAGMENT_TAG = "main_fragment";

    // TODO use this variable for reading/writing to SQLite/SharedPrefs for caching
    private ArrayList<Restaurant> restaurants;

    private int offset = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoLoader.init(this, false);
        final DoorDashDataParser.DoorDashDataService service =
                DoorDashDataParser.getDoorDashData(offset);


        displaySpinnerFragment();
        fetchRestaurants(service, this);
    }

    private void displaySpinnerFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new LoadingFragment())
                .commit();
    }

    private void fetchRestaurants(
            final DoorDashDataParser.DoorDashDataService service,
            final RestaurantListObserver observer) {
        service.getRestaurants().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<DoorDashResponse>() {
                    @Override
                    public void onSubscribe(@NonNull final Disposable d) {}

                    @Override
                    public void onNext(@NonNull final DoorDashResponse doorDashResponse) {
                        restaurants = doorDashResponse.getStores();
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        restaurants = null;
                        observer.onRestaurantListChange(restaurants);
                    }

                    @Override
                    public void onComplete() {
                        observer.onRestaurantListChange(restaurants);
                    }
                }
        );
    }

    private void showMainFragment() {
        // We manually maintain the backstack here so that the loading screen
        // is not part of the backstack.
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, MainFragment.newInstance(restaurants))
                .commit();
    }

    @Override
    public void onRestaurantClicked(final ArrayList<PopularItem> menuItems) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, RestaurantDetailsFragment.newInstance(menuItems))
                .addToBackStack(BACK_STACK_MAIN_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onRestaurantListChange(ArrayList<Restaurant> newRestaurants) {
        restaurants = newRestaurants;
        showMainFragment();
    }
}
