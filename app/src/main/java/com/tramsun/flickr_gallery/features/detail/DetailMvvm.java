package com.tramsun.flickr_gallery.features.detail;

import com.tramsun.flickr_gallery.features.base.view.MvvmView;
import com.tramsun.flickr_gallery.features.base.viewmodel.MvvmViewModel;

public interface DetailMvvm {
    interface View extends MvvmView {
        void setVersionString(String version);
    }

    interface ViewModel extends MvvmViewModel<View> {
    }
}