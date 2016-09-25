package com.tramsun.flickr_gallery.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {

    public static boolean saveImageExternal(Bitmap bitmap, String name) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
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

}
