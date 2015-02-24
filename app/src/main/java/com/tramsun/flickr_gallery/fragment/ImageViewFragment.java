package com.tramsun.flickr_gallery.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tramsun.flickr_gallery.Constants;
import com.tramsun.flickr_gallery.R;
import com.tramsun.flickr_gallery.utils.AnimUtils;
import com.tramsun.flickr_gallery.utils.FileUtils;

/**
 * Created by Tushar on 25-02-2015.
 */
public class ImageViewFragment extends BaseFragment{

    private Context context;
    private ImageView image;
    private Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ImageViewFragment newInstance(boolean isNested) {
        ImageViewFragment fragment = new ImageViewFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_NESTED, isNested);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = inflater.getContext();
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_image, container, false);
        image = (ImageView) root.findViewById(R.id.image_view);
        log.d("image="+image);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(bitmap != null) {
            AnimUtils.fadeIn(image, bitmap, 1000);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        log.d("onStart");
        if(bitmap == null) {
            bitmap = FileUtils.loadImage(context);
            log.d("onStart: image=" + image + ", bitmap=" + bitmap);
            AnimUtils.fadeIn(image, bitmap, 1000);
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
