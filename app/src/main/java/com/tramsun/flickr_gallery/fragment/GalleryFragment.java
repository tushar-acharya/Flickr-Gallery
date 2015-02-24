package com.tramsun.flickr_gallery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramsun.flickr_gallery.Constants;
import com.tramsun.flickr_gallery.R;
import com.tramsun.flickr_gallery.adapter.ImagesAdapter;
import com.tramsun.flickr_gallery.interfaces.GalleryActions;
import com.tramsun.flickr_gallery.model.ImageData;
import com.tramsun.flickr_gallery.utils.FlickrManager;
import com.tramsun.flickr_gallery.utils.KeyboardUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Tushar on 23-02-2015.
 */
public class GalleryFragment extends BaseFragment implements GalleryActions {
    @InjectView(R.id.grid) public GridView grid;
    @InjectView(R.id.search_button) public Button search;
    @InjectView(R.id.flickr_tag_name) public EditText tag;

    private ImagesAdapter adapter;
    private ArrayList<ImageData> imagesList;
    private Context context;

    public void setTagError(final String error) {
        if( tag != null && error != null && !error.isEmpty() ) {
            tag.post(new Runnable() {
                @Override
                public void run() {
                    tag.setError(error);
                }
            });
        }
    }

    Runnable getMetadata = new Runnable() {
        @Override
        public void run() {
            String searchTag = tag.getText().toString().trim();
            if (searchTag.length() >= 3) {
                FlickrManager.searchImagesByTag(GalleryFragment.this, context, searchTag);
            } else {
                setTagError("Enter 3 or more letter word!");
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_gallery, container, false);
        context = inflater.getContext();
        ButterKnife.inject(this, root);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(getMetadata).start();
                KeyboardUtils.hideKeyboard(getActivity());
            }
        });
        tag.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                search.performClick();
                return true;
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if( adapter != null)
            grid.setAdapter(adapter);
    }

    public static GalleryFragment newInstance(boolean isNested) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_NESTED, isNested);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void metaDataUpdated(ArrayList<ImageData> metaData) {
        imagesList = metaData;
        adapter = new ImagesAdapter(context, imagesList, this);
        grid.post(new Runnable() {
            @Override
            public void run() {
                grid.setAdapter(adapter);
            }
        });
    }

    @Override
    public void imageFetched(ImageData imageData) {
    }

    @Override
    public void updateAdapter() {
        if(getActivity() != null && adapter != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

}
