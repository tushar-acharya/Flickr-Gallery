package com.tramsun.flickr_gallery.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Tushar on 05-01-2015.
 */
public class KeyboardUtils {
    private static Logger log = new Logger("KeyboardUtils");

    public static void hideKeyboard(Activity activity) {
        log.e("Hiding Keyboard");
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //noinspection ConstantConditions
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
