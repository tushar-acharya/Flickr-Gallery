package com.tramsun.flickr_gallery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tramsun.flickr_gallery.Constants;
import com.tramsun.flickr_gallery.R;

/**
 * Created by Tushar on 23-02-2015.
 */
public class GalleryFragment extends BaseFragment{
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        return rootView;
    }

    public static GalleryFragment newInstance(boolean isNested) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_NESTED, isNested);
        fragment.setArguments(args);
        return fragment;
    }
}
