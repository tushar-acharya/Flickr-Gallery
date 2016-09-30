package com.tramsun.flickr_gallery.features.base.viewmodel;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tramsun.flickr_gallery.features.base.view.MvvmView;


public interface MvvmViewModel<V extends MvvmView> extends Observable {
    void attachView(V view, Bundle savedInstanceState);

    void detachView();

    void saveInstanceState(@NonNull Bundle outState);

    void restoreInstanceState(@NonNull Bundle savedInstanceState);
}
