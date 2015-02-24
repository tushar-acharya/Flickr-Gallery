package com.tramsun.flickr_gallery.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tramsun.flickr_gallery.R;
import com.tramsun.flickr_gallery.fragment.ImageViewFragment;
import com.tramsun.flickr_gallery.utils.FileUtils;

/**
 * Created by Tushar on 25-02-2015.
 */
public class ImageViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ImageViewFragment.newInstance(false))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_download:
                log.e("onOptionsItemSelected: Download clicked");
                try {
                    ImageViewFragment fragment = (ImageViewFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                    FileUtils.saveImageExternal(getApplicationContext(), fragment.getBitmap(), fragment.getBitmap().hashCode() + ".png");
                    Toast.makeText(getApplicationContext(), "File saved to SD card", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
