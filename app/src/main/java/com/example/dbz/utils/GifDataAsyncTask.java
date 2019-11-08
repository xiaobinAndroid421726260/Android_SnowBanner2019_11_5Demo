package com.example.dbz.utils;

import android.os.AsyncTask;

/**
 * description:
 *
 * @author Db_z
 * date 2019/11/8 14:49
 * @version V1.0
 */
public class GifDataAsyncTask extends AsyncTask<String, Void, byte[]> {

    @Override
    protected byte[] doInBackground(final String... params) {
        final String gifUrl = params[0];
        if (gifUrl == null)
            return null;
        try {
            return new Utils().getByte(gifUrl);
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
