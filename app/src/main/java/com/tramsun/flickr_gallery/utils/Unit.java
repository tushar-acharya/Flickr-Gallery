package com.tramsun.flickr_gallery.utils;

import android.content.Context;
import android.util.TypedValue;

public class Unit {
    public static int dpToPx(Context context, float dips) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, context.getResources().getDisplayMetrics()));
    }
}
