package com.tramsun.flickr_gallery.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlickrPhotos {
    public int page;
    public int pages;
    @SerializedName("perpage")
    public int perPage;
    public String total;
    public List<FlickrPhoto> photo;
}
