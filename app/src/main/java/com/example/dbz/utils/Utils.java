package com.example.dbz.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * description:
 *
 * @author Db_z
 * date 2019/11/8 14:42
 * @version V1.0
 */
public class Utils {

    // 根据url返回byte[]
    public byte[] getByte(final String urlPath) {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            conn.setReadTimeout(20 * 1000);
            inputStream = conn.getInputStream();
            byte[] bytes = InputStreamToBytes(inputStream);
            if (bytes.length != 0) {
                return bytes;
            }
        } catch (final IOException e) {
            Log.e("---", "---e = " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    // 将InputStream转换成byte[]
    public byte[] InputStreamToBytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        try {
            while ((is.read(readByte, 0, 1024)) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}