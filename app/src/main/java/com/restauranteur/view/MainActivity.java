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


public class MainActivity extends AppCompatActivity implements RestaurantListener {
    private static final String BACK_STACK_MAIN_FRAGMENT_TAG = "main_fragment";

    private ArrayList<Restaurant> restaurants;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoLoader.init(this, false);
        final DoorDashDataParser.DoorDashDataService service = DoorDashDataParser.getDoorDashData();


        displaySpinnerFragment();
        fetchRestaurants(service);
    }

    private void displaySpinnerFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new LoadingFragment())
                .commit();
    }

    private void fetchRestaurants(final DoorDashDataParser.DoorDashDataService service) {
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
                        showMainFragment();
                    }

                    @Override
                    public void onComplete() {
                        // TODO to add pagination, we'd replace the onComplete with a callback
                        showMainFragment();
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
}
