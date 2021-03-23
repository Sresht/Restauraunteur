package com.restauranteur.view;

import android.os.Bundle;
import android.widget.Toast;

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
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements RestaurantListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoLoader.init(this, false);
        final DoorDashDataParser.DoorDashDataService service = DoorDashDataParser.getDoorDashData();


        displaySpinnerFragment();
        displayRestaurants(service);
    }

    private void displaySpinnerFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new LoadingFragment())
                .commit();
    }

    private void displayRestaurants(final DoorDashDataParser.DoorDashDataService service) {
        service.getRestaurants().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<DoorDashResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(@NonNull DoorDashResponse doorDashResponse) {
                        showMainFragment(new ArrayList<>(doorDashResponse.getStores()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showMainFragment(null);
                    }

                    @Override
                    public void onComplete() { }
                }
        );
    }

    private void showMainFragment(@Nullable final ArrayList<Restaurant> restaurants) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, MainFragment.newInstance(restaurants))
                .commit();
    }

    @Override
    public void onRestaurantClicked(final ArrayList<PopularItem> menuItems) {
        Toast.makeText(this, menuItems.get(0).getName(), Toast.LENGTH_SHORT).show();
    }
}
