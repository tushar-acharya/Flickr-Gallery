package com.tramsun.flickr_gallery.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    protected void showProgressDialog(boolean show) {
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


    @Override protected void onDestroy() {
        super.onDestroy();

        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
