package com.tramsun.flickr_gallery.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tramsun.flickr_gallery.interfaces.GalleryActions;
import com.tramsun.flickr_gallery.model.ImageData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.tramsun.flickr_gallery.Constants.API_KEY_SEARCH_STRING;
import static com.tramsun.flickr_gallery.Constants.FLICKR_BASE_URL;
import static com.tramsun.flickr_gallery.Constants.FLICKR_GET_SIZES_STRING;
import static com.tramsun.flickr_gallery.Constants.FLICKR_PHOTOS_SEARCH_STRING;
import static com.tramsun.flickr_gallery.Constants.FORMAT_STRING;
import static com.tramsun.flickr_gallery.Constants.PHOTO_ID_STRING;
import static com.tramsun.flickr_gallery.Constants.TAGS_STRING;

public class FlickrManager {

	// String to create Flickr API urls

	private static final int FLICKR_PHOTOS_SEARCH_ID = 1;
	private static final int FLICKR_GET_SIZES_ID = 2;
	private static final int NUMBER_OF_PHOTOS = 100;

	public static final int PHOTO_THUMB = 111;
	public static final int PHOTO_LARGE = 222;

	private static String createURL(int methodId, String parameter) {
		String method_type;
		String url = null;
		switch (methodId) {
		case FLICKR_PHOTOS_SEARCH_ID:
			method_type = FLICKR_PHOTOS_SEARCH_STRING;
			url = FLICKR_BASE_URL + method_type + API_KEY_SEARCH_STRING + TAGS_STRING + URLEncoder.encode(parameter) + FORMAT_STRING + "&per_page="+NUMBER_OF_PHOTOS+"&media=photos";
			break;
		case FLICKR_GET_SIZES_ID:
			method_type = FLICKR_GET_SIZES_STRING;
			url = FLICKR_BASE_URL + method_type + PHOTO_ID_STRING + parameter + API_KEY_SEARCH_STRING + FORMAT_STRING;
			break;
		}
		return url;
	}

	public static Bitmap getImage(ImageData imgCon) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(imgCon.getLargeURL());
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (Exception e) {
			Log.e("FlickrManager", e.getMessage());
		}
		return bm;
	}

    public static class GetLargePhotoThread extends Thread {
        private final GalleryActions actions;
        ImageData imageData;

        public GetLargePhotoThread(GalleryActions actions, ImageData imageData) {
            this.actions = actions;
            this.imageData = imageData;
        }

        @Override
        public void run() {
            if (imageData.getPhoto() == null) {
                imageData.setPhoto(FlickrManager.getImage(imageData));
            }
            if (imageData.getPhoto() != null) {
                actions.imageFetched(imageData);
            }
        }
    }

	public static Bitmap getThumbnail(ImageData imgCon) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(imgCon.getThumbURL());
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (Exception e) {
			Log.e("FlickrManager", e.getMessage());
		}
		return bm;
	}

	public static class GetThumbnailsThread extends Thread {
        GalleryActions actions;
		ImageData imageData;

		public GetThumbnailsThread(GalleryActions actions, ImageData imageData) {
            this.actions = actions;
			this.imageData = imageData;
		}

		@Override
		public void run() {
			imageData.setThumb(getThumbnail(imageData));
			if (imageData.getThumb() != null) {
                actions.updateAdapter();
			}
		}

	}

	public static ArrayList<ImageData> searchImagesByTag(GalleryActions actions, Context ctx, String tag) {
		String url = createURL(FLICKR_PHOTOS_SEARCH_ID, tag);
		ArrayList<ImageData> tmp = new ArrayList<>();
		String jsonString = null;
		try {
			if (Net.isConnected(ctx)) {
				ByteArrayOutputStream baos = Net.readBytes(url);
				jsonString = baos.toString();
			}
			try {
				JSONObject root = new JSONObject(jsonString.replace("jsonFlickrApi(", "").replace(")", ""));
				JSONObject photos = root.getJSONObject("photos");
				JSONArray imageJSONArray = photos.getJSONArray("photo");
				for (int i = 0; i < imageJSONArray.length(); i++) {
					JSONObject item = imageJSONArray.getJSONObject(i);
					ImageData imgCon = new ImageData(item.getString("id"), item.getString("owner"), item.getString("secret"), item.getString("server"),
							item.getString("farm"));
                    new GetThumbnailsThread(actions, imgCon).start();
					imgCon.setPosition(i);
					tmp.add(imgCon);
				}
                actions.metaDataUpdated(tmp);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException nue) {
			nue.printStackTrace();
		}

		return tmp;
	}

}
