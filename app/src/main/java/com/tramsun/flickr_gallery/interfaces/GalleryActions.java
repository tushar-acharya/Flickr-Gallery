package com.tramsun.flickr_gallery.interfaces;

import com.tramsun.flickr_gallery.model.ImageData;

import java.util.ArrayList;

/**
 * Created by Tushar on 24-02-2015.
 */
public interface GalleryActions {
    public void metaDataUpdated(ArrayList<ImageData> metaData);
    public void imageFetched(ImageData image);
    public void updateAdapter();
}
