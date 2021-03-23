package com.restauranteur.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.widget.RecyclerCollectionComponent;
import com.facebook.litho.widget.Progress;

import com.restauranteur.model.Restaurant;
import com.restauranteur.view.component.RestaurantSection;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private static final String RESTAURANT_LIST_KEY = "RESTAURANT_KEY";

    private ArrayList<Restaurant> restaurants;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurants = getArguments().getParcelableArrayList(RESTAURANT_LIST_KEY);
        }
    }

    public static MainFragment newInstance(final ArrayList<Restaurant> restaurants) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        if (restaurants != null) {
            args.putParcelableArrayList(RESTAURANT_LIST_KEY, restaurants);
        }
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final ComponentContext c = new ComponentContext(getContext());

        if (restaurants == null) {
            // TODO throw up a litho error component or at least a toast
            return LithoView.create(
                    getContext(),
                    Progress.create(c)
                            .build());
        }

        final Component component =
                RecyclerCollectionComponent.create(c)
                .disablePTR(true)
                .section(RestaurantSection.create(new SectionContext(c)).restaurants(restaurants))
                .build();

        return LithoView.create(c, component);
    }
}
