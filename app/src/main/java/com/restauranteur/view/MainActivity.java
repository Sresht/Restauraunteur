package com.restauranteur.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.facebook.soloader.SoLoader;
import com.restauranteur.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoLoader.init(this, false);
    }
}
