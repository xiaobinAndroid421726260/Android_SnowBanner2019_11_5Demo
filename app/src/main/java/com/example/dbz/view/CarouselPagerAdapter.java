package com.example.dbz.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.example.dbz.R;
import com.example.dbz.utils.GifDataAsyncTask;
import com.felipecsl.gifimageview.library.GifImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * description:
 *
 * @author Db_z
 * date 2019/11/19 16:47
 * @version V1.0
 */
public class CarouselPagerAdapter<T> extends PagerAdapter {

    private Context mContext;
    private List<T> mData;
    private OnItemViewPagerClickListener mOnItemClickListener;

    public CarouselPagerAdapter(Context context){
        mContext = context;
        mData = new ArrayList<>();
    }

    public void setOnItemViewPagerClickListener(OnItemViewPagerClickListener onItemClick){
        this.mOnItemClickListener = onItemClick;
    }

    // 添加数据 在添加数据之前要先清空集合
    public void addData(List<T> data) {
        clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null){
            mData.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size() * 2000;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if (mData == null || mData.size() == 0) return null;
        final int newPosition = position % mData.size();
        View view = View.inflate(mContext, R.layout.item_carousel_poster, null);
        GifImageView imageView = view.findViewById(R.id.iv);
        final String url = (String) mData.get(newPosition);
        // 截取字符串网址后三位 (判断是否等于gif图) 如果等于转换成字节设置
        if (url.substring(url.length() - 3).equals("gif")) {
            try {
                byte[] bytes = new GifDataAsyncTask().execute(url).get();
                if (bytes != null) {
                    imageView.setBytes(bytes);
                    imageView.startAnimation(); //开始动画 (没有动画会不显示图片 或者 图片静态不会动)
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(mContext).load(url).into(imageView);
        }
        container.addView(view);
        view.setOnTouchListener(new View.OnTouchListener() {
            private int downX;
            private long downTime;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getX();
                        downTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        int upX = (int) event.getX();
                        long upTime = System.currentTimeMillis();
                        if (downX == upX && upTime - downTime < 500) {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(url, newPosition);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}