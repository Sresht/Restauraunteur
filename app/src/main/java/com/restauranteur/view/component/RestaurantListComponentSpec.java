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
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;
import com.restauranteur.R;
import com.restauranteur.model.PopularItem;
import com.restauranteur.model.Restaurant;
import com.restauranteur.model.Status;
import com.restauranteur.view.DoordashApiPaginator;
import com.restauranteur.view.RestaurantListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


@LayoutSpec
public class RestaurantListComponentSpec {

    @OnCreateLayout
    static Component onCreateLayout(
            final ComponentContext c,
            final @Prop DoordashApiPaginator paginator,
            final @Prop ArrayList<Restaurant> restaurants,
            final @Prop RecyclerBinder binder) {
        if (restaurants != null) {
            addRestaurantsToComponent(c, binder, restaurants);
        }
        return Recycler.create(c)
                .binder(binder)
                .onScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull final RecyclerView recyclerView, final int dx, final int dy) {
                      super.onScrolled(recyclerView, dx, dy);

                      // use the recyclerBinder to determine what items at what position are visible -
                      // (you could also use the findLastVisibleItemPosition() method depending on your implementation)
                      int lastVisibleItemPosition = binder.findLastVisibleItemPosition();

                      //check if it is within range relative to the current available items and is not loading (room to improve/modify logic pending use case)
                      if ((binder.getItemCount() - 5) <= lastVisibleItemPosition) {
                          //if so - use your service to get the next page
                          final ArrayList<Restaurant> nextRestaurants = paginator.getNextPage();
                          if (nextRestaurants != null && nextRestaurants.size() > 0) {
                              addRestaurantsToComponent(c, binder, nextRestaurants);
                          }
                      }
                    }
                })
                .build();
    }

    private static void addRestaurantsToComponent(
            final ComponentContext c,
            final RecyclerBinder binder,
            final ArrayList<Restaurant> restaurants) {
        int position = binder.getItemCount();
        for (final Restaurant curr : restaurants) {
            if (curr == null) {
                return;
            }

            final Status status = curr.getStatus();
            final ArrayList<PopularItem> menu =
                    curr.getMenus() == null ?
                            null :
                            curr.getMenus().get(0) == null ?
                                    null :
                                    curr.getMenus().get(0).getMenus();
            binder.insertItemAt(
                    position++,
                    ListItemWithImageComponent.create(c)
                            .title(curr.getName())
                            .imageUrl(curr.getCoverImageUrl())
                            .description(curr.getCuisine())
                            .subtitle(status == null ? null : getDisplayDistance(c.getApplicationContext().getResources(), status))
                            .clickHandler(RestaurantListComponent.onListItemClick(c, menu))
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
            @Prop final RestaurantListener restaurantListener
    ) {
        restaurantListener.onRestaurantClicked(popularItems);
    }

}
