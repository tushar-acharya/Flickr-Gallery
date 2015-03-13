package com.tramsun.flickr_gallery.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.tramsun.flickr_gallery.activity.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public static String getString(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        String html = "";
        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder str = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null)
        {
            str.append(line);
        }
        in.close();
        return str.toString();
    }
}
