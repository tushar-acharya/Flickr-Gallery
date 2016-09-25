package com.tramsun.flickr_gallery.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.tramsun.flickr_gallery.IntentConstants;
import com.tramsun.flickr_gallery.R;
import com.tramsun.flickr_gallery.adapter.ImagesAdapter;
import com.tramsun.flickr_gallery.data.DataUtils;
import com.tramsun.flickr_gallery.data.FlickrApi;
import com.tramsun.flickr_gallery.data.model.FlickrPhoto;
import com.tramsun.flickr_gallery.data.model.FlickrResponse;
import com.tramsun.flickr_gallery.databinding.ActivityGalleryBinding;
import com.tramsun.flickr_gallery.utils.KeyboardUtils;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class SearchActivity extends BaseActivity implements ImagesAdapter.OnImageClicked {

    private ImagesAdapter adapter;

    private ActivityGalleryBinding binding;

    private FlickrApi flickrApi;

    private ArrayList<FlickrPhoto> allImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery);
        flickrApi = DataUtils.provideFlickrApi();

        if (savedInstanceState == null) {
            allImages = new ArrayList<>();
        } else {
            allImages = (ArrayList<FlickrPhoto>) savedInstanceState.getSerializable(IntentConstants.ALL_IMAGES);
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(IntentConstants.ALL_IMAGES, allImages);
    }

    @Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        adapter = new ImagesAdapter(this, allImages, this);
        binding.imageGrid.setLayoutManager(new GridLayoutManager(this, 4));
        binding.imageGrid.setHasFixedSize(true);
        binding.imageGrid.setAdapter(adapter);

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchImages(binding.flickrTagName.getText().toString());
                KeyboardUtils.hideKeyboard(SearchActivity.this);
            }
        });

        binding.flickrTagName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                binding.searchButton.performClick();
                return true;
            }
        });
    }

    private void searchImages(String searchTag) {
        if (searchTag.length() < 3) {
            setTagError("Enter 3 or more letter word!");
            return;
        }

        setTagError(null);
        showProgressDialog(true);
        flickrApi.fetchImageListForTag(100, searchTag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FlickrResponse>() {
                    @Override public void call(FlickrResponse images) {
                        handleResponse(images);
                        showProgressDialog(false);
                    }
                }, new Action1<Throwable>() {
                    @Override public void call(Throwable throwable) {
                        showProgressDialog(false);
                    }
                });
    }

    private void handleResponse(FlickrResponse images) {
        if (images != null && images.photos != null && images.photos.photo != null) {
            adapter.setImages(images.photos.photo);
        } else {
            setTagError("No images found");
        }
    }

    public void setTagError(final String error) {
        if (binding.flickrTagName != null) {
            binding.flickrTagName.setError(error);
        }
    }

    @Override public void onImageClicked(FlickrPhoto imageData) {
        Intent intent = new Intent(this, ImageViewActivity.class);
        intent.putExtra(IntentConstants.IMAGE_URL, imageData.getImageUrl());
        startActivity(intent);
    }
}
