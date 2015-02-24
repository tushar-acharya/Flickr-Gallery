package com.tramsun.flickr_gallery.activity;

import android.os.Bundle;

import com.tramsun.flickr_gallery.R;
import com.tramsun.flickr_gallery.fragment.GalleryFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, GalleryFragment.newInstance(false))
                    .commit();
        }
    }

}
