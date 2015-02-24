package com.tramsun.flickr_gallery.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by Tushar on 25-02-2015.
 */
public class AnimUtils {
    public static void fadeIn(final ImageView i, Bitmap image) {
        fadeIn(i, image, 500);
    }

    public static void fadeIn(final ImageView i, Bitmap image, int time) {
        i.setVisibility(View.INVISIBLE);
        i.setImageBitmap(image);
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(time);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                i.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        i.startAnimation(animation);
    }
}
