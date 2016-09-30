package com.tramsun.flickr_gallery.dagger.component;


import com.tramsun.flickr_gallery.dagger.module.ActivityModule;
import com.tramsun.flickr_gallery.dagger.module.ViewModelModule;
import com.tramsun.flickr_gallery.dagger.scope.ActivityScope;
import com.tramsun.flickr_gallery.features.search.SearchActivity;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {ActivityModule.class, ViewModelModule.class}
)
public interface ActivityComponent {
    void inject(SearchActivity searchActivity);

//    void inject(ImageViewActivity imageViewActivity);
}
