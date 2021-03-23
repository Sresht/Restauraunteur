package com.restauranteur.view.component;

import android.view.LayoutInflater;

import com.facebook.litho.annotations.FromEvent;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.sections.Children;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.annotations.GroupSectionSpec;
import com.facebook.litho.sections.annotations.OnCreateChildren;
import com.facebook.litho.sections.common.DataDiffSection;
import com.facebook.litho.sections.common.RenderEvent;
import com.facebook.litho.sections.common.SingleComponentSection;
import com.facebook.litho.sections.widget.ListRecyclerConfiguration;
import com.facebook.litho.sections.widget.RecyclerCollectionComponent;
import com.facebook.litho.viewcompat.ViewCreator;
import com.facebook.litho.widget.ComponentRenderInfo;
import com.facebook.litho.widget.RenderInfo;
import com.restauranteur.R;
import com.restauranteur.model.Restaurant;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;

import static com.facebook.litho.widget.SnapUtil.SNAP_TO_CENTER;

@GroupSectionSpec
public class RestaurantSectionSpec {
    @OnCreateChildren
    static Children onCreateChildren(final SectionContext c, final @Prop ArrayList<Restaurant> restaurants) {
        return Children.create()
                .child(
                        SingleComponentSection.create(c)
                        .component(
                                RecyclerCollectionComponent.create(c)
                                .disablePTR(true)
                                .recyclerConfiguration(
                                        ListRecyclerConfiguration.create().orientation(LinearLayoutManager.HORIZONTAL).reverseLayout(false).snapMode(SNAP_TO_CENTER).build()
                                )
                                .section(
                                        DataDiffSection.<Restaurant>create(c)
                                        .data(restaurants)
                                        .build())
                                .canMeasureRecycler(true))
                        .build())
                .child(
                        DataDiffSection.<Restaurant>create(c)
                        .data(restaurants))
                .build();
    }

    @OnEvent(RenderEvent.class)
    static RenderInfo onRender(final SectionContext c, @FromEvent Restaurant model) {
        return ComponentRenderInfo.create()
                .component(
                        RestaurantItem.create(c)
                                .title(model.getName())
                                .build())
                .build();
    }

}
