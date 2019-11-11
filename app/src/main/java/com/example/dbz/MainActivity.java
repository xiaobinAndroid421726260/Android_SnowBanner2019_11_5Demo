package com.example.dbz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.dbz.snow.Snow;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainContract.View, View.OnClickListener {

    private Button mRefresh, mRefreshFail, mSwitch, mStart, mStop, mBtnFall;
    private TextView mTextView;
    private ConvenientBanner mBanner;
    private Snow mSnow;
    private ABaseTransformer mTransforemer;
    private String mTransforemerName;
    private IMainContract.Presenter mPresenter;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mPresenter = new MainPresenter(this);
        mPresenter.onAttach();
    }

    private void initView() {
        mTextView = findViewById(R.id.text);
        mBanner = findViewById(R.id.banner);
        mRefresh = findViewById(R.id.btn_net);
        mRefreshFail = findViewById(R.id.btn_net_fail);
        mSwitch = findViewById(R.id.btn_switch);
        mStart = findViewById(R.id.btn_start);
        mStop = findViewById(R.id.btn_stop);
        mSnow = findViewById(R.id.snowView);
        mBtnFall = findViewById(R.id.btn_falling);
        mRefresh.setOnClickListener(this);
        mRefreshFail.setOnClickListener(this);
        mSwitch.setOnClickListener(this);
        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);
        mBtnFall.setOnClickListener(this);
    }

    @Override
    public void showEmptyTaskError() {
        mTextView.setText("数据请求失败了！！！");
        Toast.makeText(this, "数据失败了！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTasksList() {

    }

    @Override
    public void refreshData(List<String> data) {
        if (!mPresenter.isDataMissing()) return;
        switchAnimation();
        mBanner.setPages(new CBViewHolderCreator<ViewHolder>() {
            @Override
            public ViewHolder createHolder() {
                return new ViewHolder();
            }
        }, data).setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setPageTransformer(mTransforemer)
                .startTurning(3000);
    }

    private void switchAnimation() {
        try {
            if (!mPresenter.getTransformerData().isEmpty()) {
                if (mPresenter.getTransformerData().size() == index) {
                    index = 0;
                }
                mTransforemerName = mPresenter.getTransformerData().get(index);
                Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + mTransforemerName);
                mTransforemer = (ABaseTransformer) cls.newInstance();
                mBanner.setPageTransformer(mTransforemer);
                mBanner.getViewPager().setPageTransformer(true, mTransforemer);
                //部分3D特效需要调整滑动速度
                if (mTransforemerName.equals("StackTransformer")) {
                    mBanner.setScrollDuration(1200);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net:
                mPresenter.onAttach();
                mTextView.setText("刷新了一波数据");
                Toast.makeText(this, "刷新了一波数据！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_net_fail:
                mPresenter.startFail();
                break;
            case R.id.btn_switch:
                index++;
                switchAnimation();
                break;
            case R.id.btn_start:
                mSnow.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_stop:
                mSnow.setVisibility(View.GONE);
                break;
            case R.id.btn_falling:
                startActivity(new Intent(this, FallingActivity.class));
                break;
        }
    }

    @Override
    public void setPersenter(IMainContract.Presenter persenter) {
        mPresenter = persenter;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBanner.stopTurning();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

}