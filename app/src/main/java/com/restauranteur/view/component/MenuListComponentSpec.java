package com.restauranteur.view.component;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.LinearLayoutInfo;
import com.facebook.litho.widget.Recycler;
import com.facebook.litho.widget.RecyclerBinder;
import com.restauranteur.model.PopularItem;

import java.util.ArrayList;

import androidx.recyclerview.widget.OrientationHelper;

@LayoutSpec
public class MenuListComponentSpec {
    @OnCreateLayout
    static Component onCreateLayout(
            final ComponentContext c,
            final @Prop ArrayList<PopularItem> menuItems) {
        final RecyclerBinder binder = new RecyclerBinder.Builder().layoutInfo(
                new LinearLayoutInfo(c.getApplicationContext(), OrientationHelper.VERTICAL, false)).build(c);

        if (menuItems != null) {
            addMenuItemsToComponent(c, binder, menuItems);
        }

        return Recycler.create(c).binder(binder).build();
    }

    private static void addMenuItemsToComponent(
            final ComponentContext c,
            final RecyclerBinder binder,
            final ArrayList<PopularItem> menuItems) {
        for (final PopularItem curr : menuItems) {
            binder.appendItem(
                    ListItemWithImageComponent.create(c)
                            .title(curr.getName())
                            .description(curr.getHumanReadablePrice())
                            .imageUrl(curr.getImageUrl())
                            .build());
        }
    }
}
