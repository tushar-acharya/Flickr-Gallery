package com.tramsun.flickr_gallery.data.model;

import java.io.Serializable;
import java.util.Locale;

public class FlickrPhoto implements Serializable {
    private static final String PHOTO_THUMB = "_t";
    private static final String PHOTO_LARGE = "_z";

    public String id;
    public String owner;
    public String secret;
    public String server;
    public int farm;
    public String title;

    public String getThumbnailUrl() {
        return createPhotoURL(PHOTO_THUMB);
    }

    public String getImageUrl() {
        return createPhotoURL(PHOTO_LARGE);
    }

    private String createPhotoURL(String photoType) {
        return String.format(Locale.US, "http://farm%d.staticflickr.com/%s/%s_%s%s.jpg", farm, server, id, secret, photoType);
    }
}