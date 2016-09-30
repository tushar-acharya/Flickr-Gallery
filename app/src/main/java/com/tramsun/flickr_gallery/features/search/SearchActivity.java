package com.tramsun.flickr_gallery.features.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.tramsun.flickr_gallery.BR;
import com.tramsun.flickr_gallery.IntentConstants;
import com.tramsun.flickr_gallery.R;
import com.tramsun.flickr_gallery.data.model.FlickrPhoto;
import com.tramsun.flickr_gallery.databinding.ActivityGalleryBinding;
import com.tramsun.flickr_gallery.features.base.BaseActivity;
import com.tramsun.flickr_gallery.features.detail.ImageViewActivity;
import com.tramsun.flickr_gallery.utils.KeyboardUtils;

import javax.inject.Inject;


public class SearchActivity extends BaseActivity implements SearchMvvm.View {

    private ImagesAdapter adapter;

    private ActivityGalleryBinding binding;

    @Inject
    SearchMvvm.ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery);
        binding.setVariable(BR.vm, viewModel);

        viewModel.attachView(this, savedInstanceState);

    }

    @Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        adapter = new ImagesAdapter(this, viewModel);
        binding.imageGrid.setLayoutManager(new GridLayoutManager(this, 4));
        binding.imageGrid.setHasFixedSize(true);
        binding.imageGrid.setAdapter(adapter);

        binding.flickrTagName.setOnEditorActionListener((v, actionId, event) -> {
            viewModel.search(binding.flickrTagName);
            return true;
        });
    }

    @Override
    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) {
            viewModel.saveInstanceState(outState);
        }
    }

    @Override
    public void setTagError(final String error) {
        if (binding.flickrTagName != null) {
            binding.flickrTagName.setError(error);
        }
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideKeyboard(SearchActivity.this);
    }

    @Override
    public void goToDetailActivity(FlickrPhoto imageData) {
        Intent intent = new Intent(this, ImageViewActivity.class);
        intent.putExtra(IntentConstants.IMAGE_URL, imageData.getImageUrl());
        startActivity(intent);
    }

    @Override
    public void notifyImagesChanged() {
        adapter.setImages(viewModel.getAllImages());
    }
}
