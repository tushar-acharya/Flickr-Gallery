package com.tramsun.flickr_gallery.features.search;

import com.tramsun.flickr_gallery.data.model.FlickrPhoto;
import com.tramsun.flickr_gallery.features.base.view.MvvmView;
import com.tramsun.flickr_gallery.features.base.viewmodel.MvvmViewModel;

import java.util.List;

public interface SearchMvvm {
    interface View extends MvvmView {
        void hideKeyboard();

        void goToDetailActivity(FlickrPhoto imageData);

        void notifyImagesChanged();

        void setTagError(final String error);

        void showProgressDialog(boolean show);
    }

    interface ViewModel extends MvvmViewModel<View>, ImagesAdapter.OnImageClicked {
        void search(android.view.View view);

        List<FlickrPhoto> getAllImages();
    }
}