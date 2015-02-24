package com.tramsun.flickr_gallery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tramsun.flickr_gallery.Constants;
import com.tramsun.flickr_gallery.utils.Logger;

/**
 * Created by Tushar on 23-02-2015.
 */
public class BaseFragment extends Fragment {
    public Logger log = new Logger(getClass().getSimpleName());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            boolean isNested = args.getBoolean(Constants.IS_NESTED, true);
            if(isNested) setRetainInstance(false);
            else setRetainInstance(true);
        }
    }
}
