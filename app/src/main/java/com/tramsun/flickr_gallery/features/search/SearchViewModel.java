package com.tramsun.flickr_gallery.features.search;

import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.tramsun.flickr_gallery.IntentConstants;
import com.tramsun.flickr_gallery.data.FlickrApi;
import com.tramsun.flickr_gallery.data.model.FlickrPhoto;
import com.tramsun.flickr_gallery.data.model.FlickrResponse;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SearchViewModel extends BaseObservable implements SearchMvvm.ViewModel {

    private final FlickrApi flickrApi;

    public String searchText;

    private SearchMvvm.View view;

    private ArrayList<FlickrPhoto> allImages;

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    @Override
    public ArrayList<FlickrPhoto> getAllImages() {
        return allImages;
    }

    @Inject
    public SearchViewModel(FlickrApi flickrApi) {
        this.flickrApi = flickrApi;
    }

    public void search(View button) {
        Timber.d("search: searchString = %s", searchText);

        view.hideKeyboard();

        if (searchText.length() < 3) {
            view.setTagError("Enter 3 or more letter word!");
            return;
        }

        view.setTagError(null);
        view.showProgressDialog(true);
        flickrApi.fetchImageListForTag(100, searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> view.showProgressDialog(false))
                .subscribe(this::handleResponse, Timber::e);
    }

    private void handleResponse(FlickrResponse images) {
        if (images != null && images.photos != null && images.photos.photo != null) {
            allImages = images.photos.photo;
            view.notifyImagesChanged();
        } else {
            view.setTagError("No images found");
        }
    }

    @Override
    public void attachView(SearchMvvm.View view, Bundle savedInstanceState) {
        this.view = view;
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        } else {
            allImages = new ArrayList<>();
        }
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void saveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(IntentConstants.ALL_IMAGES, allImages);
    }

    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {
        allImages = (ArrayList<FlickrPhoto>) savedInstanceState.getSerializable(IntentConstants.ALL_IMAGES);
    }

    @Override
    public void onImageClicked(FlickrPhoto imageData) {
        view.goToDetailActivity(imageData);
    }
}