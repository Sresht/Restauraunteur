package com.restauranteur.view.component;

import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;

import static com.facebook.yoga.YogaEdge.ALL;

@LayoutSpec
public class RestaurantItemSpec {
    @OnCreateLayout
    static Component onCreateLayout(
            ComponentContext c, @Prop String title) {
        return Column.create(c)
                .paddingDip(ALL, 16)
                .child(Text.create(c).text(title).textSizeSp(20))
                .build();
    }
}
