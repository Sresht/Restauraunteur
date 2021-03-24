package com.restauranteur.view;

import android.os.Bundle;

import com.facebook.soloader.SoLoader;
import com.restauranteur.R;
import com.restauranteur.constant.DoordashApiConstants;
import com.restauranteur.model.DoorDashResponse;
import com.restauranteur.model.PopularItem;
import com.restauranteur.model.Restaurant;
import com.restauranteur.parser.DoorDashDataParser;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements RestaurantListener, DoordashApiPaginator {
    private static final String BACK_STACK_MAIN_FRAGMENT_TAG = "main_fragment";

    private ArrayList<Restaurant> restaurants;
    private int offset = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoLoader.init(this, false);


        final DoorDashDataParser.DoorDashDataService service = DoorDashDataParser.getDoorDashData(offset);

        final ArrayList<Restaurant> restaurants = getRestaurants(service, true);

        displaySpinnerFragment();
        showMainFragment(restaurants);
    }

    private void displaySpinnerFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new LoadingFragment())
                .commit();
    }

    public ArrayList<Restaurant> getRestaurants(final DoorDashDataParser.DoorDashDataService service, final boolean shouldDisplay) {
        service.getRestaurants().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<DoorDashResponse>() {
                    @Override
                    public void onSubscribe(@NonNull final Disposable d) { }

                    @Override
                    public void onNext(@NonNull final DoorDashResponse doorDashResponse) {
                        restaurants = doorDashResponse.getStores();
                        if (shouldDisplay) {
                            showMainFragment(restaurants);
                        }
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        restaurants = null;
                    }

                    @Override
                    public void onComplete() { }
                }
        );
        return restaurants;
    }

    private void showMainFragment(@Nullable final ArrayList<Restaurant> restaurants) {
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
    public final ArrayList<Restaurant> getNextPage() {
        final DoorDashDataParser.DoorDashDataService service = DoorDashDataParser.getDoorDashData(++offset);
        return getRestaurants(service, false);
    }

    @Override
    public int getOffset() {
        return offset * DoordashApiConstants.DOORDASH_API_RESTAURANTS_LIMIT;
    }

}
