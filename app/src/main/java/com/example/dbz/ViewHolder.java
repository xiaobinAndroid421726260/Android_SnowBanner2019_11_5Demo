package com.example.dbz;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.dbz.utils.GifDataAsyncTask;
import com.felipecsl.gifimageview.library.GifImageView;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

/**
 * description:
 *
 * @author Db_z
 * date 2019/11/8 14:39
 * @version V1.0
 */
public class ViewHolder implements Holder<String> {

    private GifImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new GifImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        if (data.length() > 3) {
//          og.e("截取后三位data = " + data.substring(data.length() - 3));
//            if (data.substring(data.length() - 3).equals("gif")) {
//                try {
//                    byte[] bytes = new GifDataAsyncTask().execute(data).get();
//                    imageView.setBytes(bytes);
//                    imageView.startAnimation();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } else {
                Picasso.with(context).load(data).into(imageView);
//            }
        }
    }
}
