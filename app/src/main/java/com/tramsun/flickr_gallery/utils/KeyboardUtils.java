package com.tramsun.flickr_gallery.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import timber.log.Timber;

public class KeyboardUtils {
    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //noinspection ConstantConditions
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            Timber.d("Keyboard Hidden");
        } catch (Exception e) {
            Timber.e(e, "Failed to hide keyboard");
        }
    }
}
