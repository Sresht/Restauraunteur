package com.restauranteur.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.restauranteur.model.PopularItem;
import com.restauranteur.parser.DoorDashDataParser;
import com.restauranteur.view.component.MenuListComponent;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RestaurantDetailsFragment extends Fragment {

    private static final String MENU_ITEMS_KEY = "MENU_ITEMS_KEY";

    private ArrayList<PopularItem> menuItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DoorDashDataParser.getDoorDashData();
        if (getArguments() != null) {
            menuItems = getArguments().getParcelableArrayList(MENU_ITEMS_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final ComponentContext c = new ComponentContext(getContext());

        return LithoView.create(c,
                MenuListComponent.create(c)
                        .menuItems(menuItems)
                        .build());
    }

    public RestaurantDetailsFragment() {}

    public static RestaurantDetailsFragment newInstance(final ArrayList<PopularItem> menuItems) {
        final RestaurantDetailsFragment fragment = new RestaurantDetailsFragment();
        final Bundle args = new Bundle();
        if (menuItems != null) {
            args.putParcelableArrayList(MENU_ITEMS_KEY, menuItems);
        }
        fragment.setArguments(args);
        return fragment;
    }
}
