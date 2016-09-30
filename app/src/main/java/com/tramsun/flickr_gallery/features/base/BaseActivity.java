package com.tramsun.flickr_gallery.features.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tramsun.flickr_gallery.FlickrGalleryApplication;
import com.tramsun.flickr_gallery.dagger.component.ActivityComponent;
import com.tramsun.flickr_gallery.dagger.component.DaggerActivityComponent;
import com.tramsun.flickr_gallery.dagger.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    ActivityComponent component;

    public ActivityComponent getComponent() {
        return component;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActivityComponent();
    }

    private void createActivityComponent() {
        component = DaggerActivityComponent.builder()
                .appComponent(((FlickrGalleryApplication) getApplication()).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public void showProgressDialog(boolean show) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Please Wait..");
        }

        if (show) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
