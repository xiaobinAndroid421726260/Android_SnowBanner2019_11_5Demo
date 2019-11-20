package com.example.dbz.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.example.dbz.R;
import com.example.dbz.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的可滚动的viewpager
 */
@SuppressLint({"HandlerLeak", "ViewConstructor"})
public class CarouselDiagramViewPager<T> extends ViewPager {

    private Context mContext;
    private List<T> mDatas;
    private List<View> mListPoint;
    private RunnableTask mRunnableTask;
    private CarouselPagerAdapter<T> mAdapter;
    private LinearLayout mViewpagerContainer;       // ViewPager 的容器
    private View mLayoutScrollView;
    private int mCurrentPosition;                   // 当前点
    private int downX;
    private int downY;

    /**
     * 指示器的布局
     */
    private LinearLayout mLayoutPoint;


    /**
     * 1、要想重新刷新数据，首先拿到集合之前要清空之前的集合数据  刷新数据
     * 2、拿到之后根据集合的大小去初始化视图下面的标记，在初始化viewpager
     * 3、添加视图，刷新视图 view.addView();
     */
    public CarouselDiagramViewPager(Context context) {
        super(context);
        this.mContext = context;
        this.mDatas = new ArrayList<>();
        mAdapter = new CarouselPagerAdapter<>(mContext);
        this.setAdapter(mAdapter);
        initView();
    }

    /**
     * @param data 这里泛型可以用做实体类， 可以在具体列表适配器里面修改实体类的具体类型
     */
    public void addData(List<T> data) {
        if (data == null || data.size() == 0) return;
        mDatas.clear();
        mDatas.addAll(data);
        mAdapter.addData(mDatas);     // 添加数据之前要先清空集合
        initSelected();               // 重新初始化下面标点
        initData();
    }

    // 初始化视图下边的标点
    public void initView() {
        mLayoutScrollView = View.inflate(mContext, R.layout.layout_roll_viewpager, null);
        mViewpagerContainer = (LinearLayout) mLayoutScrollView.findViewById(R.id.ll_viewpager_container);
        mLayoutPoint = (LinearLayout) mLayoutScrollView.findViewById(R.id.ll_dots);
    }

    // 初始化下面标点
    public void initSelected() {
        initPoint(mLayoutPoint);
        mViewpagerContainer.removeAllViews();
        mViewpagerContainer.addView(this);
        mCurrentPosition = mDatas.size() * 1000;
        this.setCurrentItem(mCurrentPosition);
        startScroll();
    }

    // 返回初始化之后的视图
    public View getContentView() {
        return mLayoutScrollView;
    }


    /**
     * 初始化指示器的点
     */
    private void initPoint(LinearLayout point) {
        point.removeAllViews();
        mListPoint = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            View view = new View(mContext);
            view.setBackgroundResource(i == 0 ? R.drawable.dot_focus : R.drawable.dot_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.dip2px(mContext, 6),
                    Utils.dip2px(mContext, 6));
            if (i == (mDatas.size() - 1)) {
                params.setMargins(Utils.dip2px(mContext, 4), 0, 0, 0);
            } else {
                params.setMargins(Utils.dip2px(mContext, 4), 0, Utils.dip2px(mContext, 4), 0);
            }
            view.setLayoutParams(params);
            point.addView(view);
            mListPoint.add(view);
        }
    }

    // 初始化viewPager
    private void initData() {
        mRunnableTask = new RunnableTask();
        this.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (mDatas == null || mDatas.size() == 0) return;
                mCurrentPosition = position;
                position = position % mDatas.size();
                for (int i = 0; i < mDatas.size(); i++) {
                    if (position == i) {
                        CarouselDiagramViewPager.this.mListPoint.get(i).setBackgroundResource(R.drawable.dot_focus);
                    } else {
                        CarouselDiagramViewPager.this.mListPoint.get(i).setBackgroundResource(R.drawable.dot_normal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 开始自动轮播
     */
    private void startScroll() {
        mHandler.postDelayed(mRunnableTask, 3000);
    }


    /**
     * 停止自动轮播
     */
    private void stopScroll() {
        if (this != null) {
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
            }
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            CarouselDiagramViewPager.this.setCurrentItem(mCurrentPosition);
            startScroll();
        }
    };

    class RunnableTask implements Runnable {

        @Override
        public void run() {
            mCurrentPosition++;
            mHandler.obtainMessage().sendToTarget();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.getParent().requestDisallowInterceptTouchEvent(true);
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                stopScroll();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                if (Math.abs(moveX - downX) > Math.abs(moveY - downY)) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                startScroll();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        stopScroll();
        super.onDetachedFromWindow();
    }

    /**
     * 每一条滚动轮播图的点击事件
     */
    public void setOnItemViewPagerClickListener(OnItemViewPagerClickListener itemClickListener) {
        if (mAdapter != null) {
            mAdapter.setOnItemViewPagerClickListener(itemClickListener);
        }
    }
}