package com.restauranteur.view;

import android.os.Bundle;

import com.facebook.soloader.SoLoader;
import com.restauranteur.R;
import com.restauranteur.parser.DoorDashDataParser;
import com.restauranteur.parser.DoorDashResponse;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

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
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content, MainFragment.newInstance(new ArrayList<>(doorDashResponse.getStores())))
                                .commit();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // TODO display a toast
                        displaySpinnerFragment();
                    }

                    @Override
                    public void onComplete() { }
                }
        );

    }
}
