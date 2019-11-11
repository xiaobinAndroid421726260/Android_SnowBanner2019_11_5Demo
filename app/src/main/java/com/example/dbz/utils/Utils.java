package com.example.dbz.utils;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description:
 *
 * @author Db_z
 * date 2019/11/8 14:42
 * @version V1.0
 */
public class Utils {

    private static Utils sInstance;

    public static Utils getsInstance() {
        if (sInstance == null) {
            synchronized (Utils.class) {
                if (sInstance == null) {
                    sInstance = new Utils();
                }
            }
        }
        return sInstance;
    }

    // 根据url返回byte[]
    public byte[] getByte(final String urlString) {
        InputStream in = null;
        try {
            OkHttpClient client = new OkHttpClient();
            final String decodedUrl = URLDecoder.decode(urlString, "UTF-8");
            final URL url = new URL(decodedUrl);
            final Request request = new Request.Builder().url(url).build();
            final Response response = client.newCall(request).execute();
            in = response.body().byteStream();
            return IOUtils.toByteArray(in);
        } catch (final MalformedURLException e) {
        } catch (final OutOfMemoryError e) {
        } catch (final UnsupportedEncodingException e) {
        } catch (final IOException e) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//    // 根据url返回byte[]
//    public byte[] getByte(final String urlPath) {
//        InputStream inputStream = null;
//        try {
//            URL url = new URL(urlPath);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setConnectTimeout(20 * 1000);
//            conn.setReadTimeout(20 * 1000);
//            inputStream = conn.getInputStream();
//            byte[] bytes = InputStreamToBytes(inputStream);
//            if (bytes.length != 0) {
//                return bytes;
//            }
//        } catch (final IOException e) {
//            Log.e("---", "---e = " + e.getMessage());
//        } finally {
//            try {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }


//    // 将InputStream转换成byte[]
//    public byte[] InputStreamToBytes(InputStream is) {
//        String str = "";
//        byte[] readByte = new byte[1024];
//        try {
//            while ((is.read(readByte, 0, 1024)) != -1) {
//                str += new String(readByte).trim();
//            }
//            return str.getBytes();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}