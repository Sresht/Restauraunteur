package com.restauranteur.view.component;

import android.content.res.Resources;

import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.LinearLayoutInfo;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;
import com.restauranteur.R;
import com.restauranteur.model.PopularItem;
import com.restauranteur.model.Restaurant;
import com.restauranteur.model.Status;
import com.restauranteur.view.RestaurantOnClickHandler;

import java.util.ArrayList;

import androidx.recyclerview.widget.OrientationHelper;


@LayoutSpec
public class RestaurantListComponentSpec {
    @OnCreateLayout
    static Component onCreateLayout(
            final ComponentContext c,
            final @Prop ArrayList<Restaurant> restaurants) {
        final RecyclerBinder binder = new RecyclerBinder.Builder().layoutInfo(
                new LinearLayoutInfo(c.getApplicationContext(), OrientationHelper.VERTICAL, false)).build(c);

        if (restaurants != null) {
            addRestaurantsToComponent(c, binder, restaurants);
        }

        return Recycler.create(c).binder(binder).build();
    }

    private static void addRestaurantsToComponent(
            final ComponentContext c,
            final RecyclerBinder binder,
            final ArrayList<Restaurant> restaurants) {
        for (final Restaurant curr : restaurants) {
            binder.appendItem(
                    ListItemWithImageComponent.create(c)
                            .title(curr.getName())
                            .imageUrl(curr.getCoverImageUrl())
                            .description(curr.getCuisine())
                            .subtitle(getDisplayDistance(c.getApplicationContext().getResources(), curr.getStatus()))
                            .clickHandler(RestaurantListComponent.onListItemClick(c, curr.getMenus().get(0).getMenus()))
                            .build());
        }
    }

    private static String getDisplayDistance(final Resources res, final Status status) {
        if (status.getUnavailableReason() != null) {
            return res.getString(R.string.restaurant_closed);
        }
        return res.getQuantityString(R.plurals.restaurant_minutes_away, status.getMinutesAway(), status.getMinutesAway());
    }

    @OnEvent(ClickEvent.class)
    static void onListItemClick(
            final ComponentContext c,
            @Param final ArrayList<PopularItem> popularItems,
            @Prop final RestaurantOnClickHandler restaurantOnClickHandler
    ) {
        restaurantOnClickHandler.onRestaurantClicked(popularItems);
    }

}
