package com.restauranteur.view.component;

import android.graphics.Color;
import android.graphics.Typeface;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.litho.Border;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.fresco.FrescoImage;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaEdge;
import com.restauranteur.R;

import io.reactivex.annotations.Nullable;

import static com.facebook.yoga.YogaEdge.ALL;
import static com.facebook.yoga.YogaEdge.RIGHT;
import static com.facebook.yoga.YogaEdge.TOP;

/**
 * This row item layout spec includes an image, title, and subtitle with a thin bottom border.
 * If the image URL fails to be retrieved, there is a generic placeholder image displayed.
 */

@LayoutSpec
public class ListItemWithImageComponentSpec {
    @OnCreateLayout
    static Component onCreateLayout(
            final ComponentContext c,
            // TODO pass ID in too - just to be a unique id, even if unused
            @Prop final String title,
            @Prop @Nullable final String imageUrl,
            @Prop(optional = true) final String description,
            @Prop(optional = true) final String subtitle
    ) {
        Fresco.initialize(c.getApplicationContext());

        final DraweeController controller =
                Fresco.newDraweeControllerBuilder().setUri(imageUrl).build();

        return Row.create(c)
                .paddingDip(ALL, 16)
                .child(
                        FrescoImage.create(c)
                                .placeholderImageRes(R.drawable.placeholder_restaurant_cover)
                                .controller(controller)
                                .widthPercent(30)
                                .heightDip(70)
                                .paddingDip(RIGHT, 30))
                .flex(1)
                .child(
                        Column.create(c)
                                .child(
                                        Text.create(c)
                                                .text(title)
                                                .textStyle(Typeface.BOLD)
                                                .textSizeSp(20))
                                .child(Text.create(c).text(description).textSizeSp(14))
                                .paddingDip(TOP, 15)
                                .widthPercent(55)
                                .flex(1))
                .child(
                        Text.create(c)
                                .text(subtitle)
                                .widthPercent(15)
                                .textSizeSp(14)
                                .paddingDip(TOP, 40))
                .border(
                        Border.create(c)
                                .widthDip(YogaEdge.BOTTOM, 1)
                                .color(YogaEdge.BOTTOM, Color.LTGRAY)
                                .build())
                .build();
    }
}
