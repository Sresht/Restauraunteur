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

import static com.facebook.yoga.YogaEdge.ALL;
import static com.facebook.yoga.YogaEdge.RIGHT;
import static com.facebook.yoga.YogaEdge.TOP;

@LayoutSpec
public class MenuItemComponentSpec {
    @OnCreateLayout
    static Component onCreateLayout(
            final ComponentContext c,
            @Prop final String title,
            @Prop final String price,
            @Prop final String imageUrl
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
                                .child(Text.create(c).text(title).textSizeSp(14))
                                .paddingDip(TOP, 15)
                                .widthPercent(55)
                                .flex(1))
                .child(
                        Text.create(c)
                                .text(price)
                                .widthDip(70)
                                .textSizeSp(14)
                                .widthPercent(15)
                                .paddingDip(TOP, 40))
                .border(
                        Border.create(c)
                                .widthDip(YogaEdge.BOTTOM, 1)
                                .color(YogaEdge.BOTTOM, Color.LTGRAY)
                                .build())
                .build();
    }
}
