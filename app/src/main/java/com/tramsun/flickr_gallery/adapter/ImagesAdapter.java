package com.tramsun.flickr_gallery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.tramsun.flickr_gallery.R;
import com.tramsun.flickr_gallery.interfaces.GalleryActions;
import com.tramsun.flickr_gallery.model.ImageData;
import com.tramsun.flickr_gallery.utils.AnimUtils;
import com.tramsun.flickr_gallery.utils.FlickrManager;
import com.tramsun.flickr_gallery.utils.Unit;

import java.util.ArrayList;

/**
 * Created by Tushar on 24-02-2015.
 */
public class ImagesAdapter extends BaseAdapter{
    private final Context mContext;
    private ArrayList<ImageData> imageDatas;
    private final GalleryActions actions;

    public ImagesAdapter(Context c, ArrayList<ImageData> imageDatas, GalleryActions actions) {
        this.mContext = c;
        this.imageDatas = imageDatas;
        this.actions = actions;
    }

    public int getCount() {
        return imageDatas.size();
    }

    public ImageData getItem(int position) {
        return imageDatas.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView i;
        ImageData data = imageDatas.get(position);
        if(convertView != null) {
            i= (ImageView) convertView;
        } else {
            i = new ImageView(mContext);
            GridView.LayoutParams lp = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Unit.dpToPx(mContext, 150));
            i.setScaleType(ImageView.ScaleType.CENTER_CROP);
            i.setLayoutParams(lp);
        }
        if (imageDatas.get(position).getThumb() != null) {
            if(data.isLoadAnimationDone()) {
                i.setImageBitmap(data.getThumb());
            } else {
                AnimUtils.fadeIn(i, data.getThumb());
                data.setLoadAnimationDone(true);
            }
        } else {
            i.setImageDrawable(mContext.getResources().getDrawable(R.color.lighter_gray));
            new FlickrManager.GetThumbnailsThread(actions, data).start();
        }
        return i;
    }
}
