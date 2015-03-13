package com.tramsun.flickr_gallery.model;

import android.graphics.Bitmap;

import com.tramsun.flickr_gallery.utils.ImageLibAPIManager;

public class ImageData {
    int position;
    String id;
    Bitmap thumb;
    Bitmap photo;
    String thumbURL;
    String largeURL;
    String owner;
    String secret;
    String server;
    String farm;
    boolean loadAnimationDone = false;

    public ImageData() {
    }

    public ImageData(String id, String owner, String secret, String server, String farm) {
        super();
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        setThumbURL(createPhotoURL(ImageLibAPIManager.PHOTO_THUMB, this));
        setLargeURL(createPhotoURL(ImageLibAPIManager.PHOTO_LARGE, this));
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getLargeURL() {
        return largeURL;
    }

    public void setLargeURL(String largeURL) {
        this.largeURL = largeURL;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "position=" + position +
                ", id='" + id + '\'' +
                ", thumb=" + thumb +
                ", photo=" + photo +
                ", thumbURL='" + thumbURL + '\'' +
                ", largeURL='" + largeURL + '\'' +
                ", owner='" + owner + '\'' +
                ", secret='" + secret + '\'' +
                ", server='" + server + '\'' +
                ", farm='" + farm + '\'' +
                ", loadAnimationDone=" + loadAnimationDone +
                '}';
    }

    private String createPhotoURL(int photoType, ImageData imgCon) {
        String tmp = "http://farm" + imgCon.farm + ".staticflickr.com/" + imgCon.server + "/" + imgCon.id + "_" + imgCon.secret;// +".jpg";
        switch (photoType) {
            case ImageLibAPIManager.PHOTO_THUMB:
                tmp += "_t";
                break;
            case ImageLibAPIManager.PHOTO_LARGE:
                tmp += "_z";
                break;

        }
        tmp += ".jpg";
        return tmp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setLoadAnimationDone(boolean loadAnimationDone) {
        this.loadAnimationDone = loadAnimationDone;
    }

    public boolean isLoadAnimationDone() {
        return loadAnimationDone;
    }
}