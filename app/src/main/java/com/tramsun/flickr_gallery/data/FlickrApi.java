package com.tramsun.flickr_gallery.data;

import com.tramsun.flickr_gallery.data.model.FlickrResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrApi {
    @GET("/services/rest/?method=flickr.photos.search&api_key=0b1196e95ecd4e12f92d608931cde65d&format=json&media=photos&nojsoncallback=?") Observable<FlickrResponse> fetchImageListForTag(@Query("per_page") int count, @Query("tags") String tag);
}
