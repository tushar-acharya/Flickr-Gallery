package com.tramsun.flickr_gallery.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tushar on 14-12-2014.
 */
public class Net {
    public static boolean isConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static ByteArrayOutputStream readBytes(String urlS) {
        ByteArrayOutputStream baos = null;
        InputStream is = null;
        HttpURLConnection httpURLConnection = null;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
                System.setProperty("http.keepAlive", "false");
            }
            URL url = new URL(urlS);
            Log.i("URL", url.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            int response = httpURLConnection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                int CONNECT_TIMEOUT_MS = 5000;
                httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
                int READ_TIMEOUT_MS = 15000;
                httpURLConnection.setReadTimeout(READ_TIMEOUT_MS);
                is = new BufferedInputStream(httpURLConnection.getInputStream());

                int size = 1024;
                byte[] buffer = new byte[size];

                baos = new ByteArrayOutputStream();
                int read;
                while ((read = is.read(buffer)) != -1) {
                    if (read > 0) {
                        baos.write(buffer, 0, read);
                        buffer = new byte[size];
                    }

                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return baos;
    }
}
