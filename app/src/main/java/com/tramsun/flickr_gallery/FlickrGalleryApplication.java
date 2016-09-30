package com.tramsun.flickr_gallery;

import android.app.Application;

import com.tramsun.flickr_gallery.dagger.component.AppComponent;
import com.tramsun.flickr_gallery.dagger.component.DaggerAppComponent;
import com.tramsun.flickr_gallery.dagger.module.AppModule;

import timber.log.Timber;

public class FlickrGalleryApplication extends Application {
    AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        initComponent();
    }

    private void initComponent() {
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }
}