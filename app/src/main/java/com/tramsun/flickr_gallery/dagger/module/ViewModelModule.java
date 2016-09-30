package com.tramsun.flickr_gallery.dagger.module;

import com.tramsun.flickr_gallery.features.detail.DetailMvvm;
import com.tramsun.flickr_gallery.features.detail.DetailViewModel;
import com.tramsun.flickr_gallery.features.search.SearchMvvm;
import com.tramsun.flickr_gallery.features.search.SearchViewModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract SearchMvvm.ViewModel provideSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    abstract DetailMvvm.ViewModel provideDetailViewModel(DetailViewModel detailViewModel);
}