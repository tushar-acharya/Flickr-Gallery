package com.tramsun.flickr_gallery;

@SuppressWarnings("UnusedDeclaration")
public class Constants {
    public static final boolean LOG_DETAILED = false;

    public static final int LOG_LEVEL_NONE  = 0x00000000;
    public static final int LOG_LEVEL_ERROR = 0x00000001;
    public static final int LOG_LEVEL_DEBUG = 0x00000010;
    public static final int LOG_LEVEL_ALL   = 0x00000011;

    public static final int LOG_LEVEL = LOG_LEVEL_ALL;

    public static final String IMAGES_DATA_PREF = "IMAGES_DATA_PREF";
    public static final String IS_NESTED = "IS_NESTED";
    public static final String FLICKR_API_KEY = "0b1196e95ecd4e12f92d608931cde65d";

    public static final String FLICKR_BASE_URL = "https://api.flickr.com/services/rest/?method=";
    public static final String FLICKR_PHOTOS_SEARCH_STRING = "flickr.photos.search";
    public static final String FLICKR_GET_SIZES_STRING = "flickr.photos.getSizes";
    public static final String API_KEY_SEARCH_STRING = "&api_key="+ FLICKR_API_KEY;
    public static final String TAGS_STRING = "&tags=";
    public static final String PHOTO_ID_STRING = "&photo_id=";
    public static final String FORMAT_STRING = "&format=json";
    public static final String IMAGE_DATA = "IMAGE_DATA";
    public static final String IMAGE_CACHE_FILE_NAME = "cachedImage.png";
}
