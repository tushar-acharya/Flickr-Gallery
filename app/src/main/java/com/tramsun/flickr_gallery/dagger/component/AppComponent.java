package com.tramsun.flickr_gallery.dagger.component;

import com.tramsun.flickr_gallery.FlickrGalleryApplication;
import com.tramsun.flickr_gallery.dagger.module.AppModule;
import com.tramsun.flickr_gallery.dagger.module.ViewModelModule;
import com.tramsun.flickr_gallery.data.FlickrApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ViewModelModule.class})
public interface AppComponent {
    FlickrGalleryApplication provideApplication();

    FlickrApi provideFlickrApi();
}
