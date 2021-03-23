package com.restauranteur.view.component;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.fresco.FrescoImage;
import com.facebook.litho.widget.Text;
import com.restauranteur.R;
import com.restauranteur.model.PopularItem;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

import static com.facebook.yoga.YogaEdge.ALL;

@LayoutSpec
public class RestaurantItemSpec {
    @OnCreateLayout
    static Component onCreateLayout(
            final ComponentContext c,
            @Prop final String title,
            @Prop @Nullable final String coverImageUrl,
            @Prop final String cuisine,
            @Prop final String displayDistance,
            @Prop final ArrayList<PopularItem> popularItems
            ) {
        Fresco.initialize(c.getApplicationContext());
        
        final DraweeController controller =
                        Fresco.newDraweeControllerBuilder().setUri(coverImageUrl).build();

        return Column.create(c)
                .paddingDip(ALL, 16)
                .child(Text.create(c).text(title).textSizeSp(20))
                .child(Text.create(c).text(cuisine).textSizeSp(12))
                .child(Text.create(c).text(displayDistance).textSizeSp(10))
                .child(
                        FrescoImage.create(c)
                                .placeholderImageRes(R.drawable.placeholder_restaurant_cover)
                                .controller(controller)
                                .widthDip(60)
                                .heightDip(40))
                .build();
    }
}
