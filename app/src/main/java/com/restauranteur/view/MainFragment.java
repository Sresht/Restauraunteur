package com.restauranteur.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.LinearLayoutInfo;
import com.facebook.litho.widget.RecyclerBinder;
import com.facebook.litho.widget.Text;
import com.restauranteur.R;
import com.restauranteur.model.Restaurant;
import com.restauranteur.view.component.RestaurantListComponent;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;

public class MainFragment extends Fragment {

    private static final String RESTAURANT_LIST_KEY = "RESTAURANT_KEY";

    private ArrayList<Restaurant> restaurants;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurants = getArguments().getParcelableArrayList(RESTAURANT_LIST_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final ComponentContext c = new ComponentContext(getContext());
        if (restaurants == null) {
            // TODO be more descriptive based on the error.
            // TODO style this to look a bit nicer.
            return LithoView.create(
                    getContext(),
                    Text.create(c)
                            .textRes(R.string.user_facing_error)
                            .build());
        }


        final RecyclerBinder binder = new RecyclerBinder.Builder()
                .layoutInfo(
                        new LinearLayoutInfo(
                                getContext(),
                                OrientationHelper.VERTICAL,
                                false))
                .build(c);

        return LithoView.create(c,
                RestaurantListComponent.create(c)
                        .binder(binder)
                        .restaurants(restaurants)
                        .restaurantListener((MainActivity) getActivity())
                        .paginator((MainActivity) getActivity())
                        .build());
    }

    public MainFragment() {}

    public static MainFragment newInstance(final ArrayList<Restaurant> restaurants) {
        final MainFragment fragment = new MainFragment();
        final Bundle args = new Bundle();
        if (restaurants != null) {
            args.putParcelableArrayList(RESTAURANT_LIST_KEY, restaurants);
        }
        fragment.setArguments(args);
        return fragment;
    }
}


