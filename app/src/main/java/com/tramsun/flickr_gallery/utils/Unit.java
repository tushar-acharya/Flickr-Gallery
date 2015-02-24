package com.tramsun.flickr_gallery.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Tushar on 24-02-2015.
 */
public class Unit {
    @SuppressWarnings("UnusedDeclaration")
    public static int dpToPx( Context context, float dips ) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, context.getResources().getDisplayMetrics()));
    }
}
