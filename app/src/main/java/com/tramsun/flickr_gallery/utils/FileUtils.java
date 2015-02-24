package com.tramsun.flickr_gallery.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.tramsun.flickr_gallery.Constants;
import com.tramsun.flickr_gallery.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Tushar on 25-02-2015.
 */
public class FileUtils {
    public static boolean saveImageCache(Context context, Bitmap bitmap) {
        File directory = context.getDir("imageDir", Context.MODE_PRIVATE);
        File path = new File(directory,Constants.IMAGE_CACHE_FILE_NAME);
        return saveImageTo(path, bitmap);
    }

    public static boolean saveImageExternal(Context context, Bitmap bitmap, String name) {
        File dir = new File(Environment.getExternalStorageDirectory().toString()+"/"+context.getString(R.string.app_name));
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        File path = new File(dir, name);
        return saveImageTo(path, bitmap);
    }

    private static boolean saveImageTo(File path, Bitmap bitmap) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static File loadFile(Context context) {
        File directory = context.getDir("imageDir", Context.MODE_PRIVATE);
        return new File(directory.getPath(), Constants.IMAGE_CACHE_FILE_NAME);
    }

    public static Bitmap loadImage(Context context)
    {
        try {
            File f=loadFile(context);
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
