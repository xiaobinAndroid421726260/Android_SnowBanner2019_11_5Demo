package com.example.dbz;

import com.example.dbz.constant.HttpUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author Db_z
 * date 2019/11/8 13:29
 * @version V1.0
 */
public class MainPresenter implements IMainContract.Presenter{

    private final List<String> TransformerDatas = new ArrayList<>();
    private List<String> mBannerDatas = new ArrayList<>();
    private boolean isDataMissing;
    private IMainContract.View mMainView;

    public MainPresenter (IMainContract.View view){
        mMainView = view;
        initTransformer();
    }

    private void initTransformer(){
        TransformerDatas.add(Transformer.DefaultTransformer.getClassName());
        TransformerDatas.add(Transformer.AccordionTransformer.getClassName());
        TransformerDatas.add(Transformer.BackgroundToForegroundTransformer.getClassName());
        TransformerDatas.add(Transformer.CubeInTransformer.getClassName());
        TransformerDatas.add(Transformer.CubeOutTransformer.getClassName());
        TransformerDatas.add(Transformer.DepthPageTransformer.getClassName());
        TransformerDatas.add(Transformer.FlipHorizontalTransformer.getClassName());
        TransformerDatas.add(Transformer.FlipVerticalTransformer.getClassName());
        TransformerDatas.add(Transformer.ForegroundToBackgroundTransformer.getClassName());
        TransformerDatas.add(Transformer.RotateDownTransformer.getClassName());
        TransformerDatas.add(Transformer.RotateUpTransformer.getClassName());
        TransformerDatas.add(Transformer.StackTransformer.getClassName());
        TransformerDatas.add(Transformer.TabletTransformer.getClassName());
        TransformerDatas.add(Transformer.ZoomInTransformer.getClassName());
        TransformerDatas.add(Transformer.ZoomOutSlideTransformer.getClassName());
        TransformerDatas.add(Transformer.ZoomOutTranformer.getClassName());
    }

    @Override
    public List<String> getTransformerData() {
        return TransformerDatas;
    }

    public void startTask() {
        mBannerDatas.clear();
        mBannerDatas.add(HttpUrl.banner_1);
//        mBannerDatas.add(HttpUrl.banner_2);
        mBannerDatas.add(HttpUrl.banner_3);
        mBannerDatas.add(HttpUrl.banner_4);
        mBannerDatas.add(HttpUrl.banner_5);
        mBannerDatas.add(HttpUrl.banner_6);
        mBannerDatas.add(HttpUrl.banner_7);
        isDataMissing = true;
        mMainView.refreshData(mBannerDatas);
        mMainView.initBannerData(mBannerDatas);
    }

    @Override
    public void startFail() {
        isDataMissing = false;
        mMainView.showEmptyTaskError();
    }

    @Override
    public boolean isDataMissing() {
        return isDataMissing;
    }

    @Override
    public void onAttach() {
        if (mMainView != null){
            startTask();
        }
    }

    @Override
    public void onDestroy() {
        TransformerDatas.clear();
        mBannerDatas.clear();
    }

    public enum Transformer {
        DefaultTransformer("DefaultTransformer"),
        AccordionTransformer("AccordionTransformer"),
        BackgroundToForegroundTransformer("BackgroundToForegroundTransformer"),
        CubeInTransformer("CubeInTransformer"),
        CubeOutTransformer("CubeOutTransformer"),
        DepthPageTransformer("DepthPageTransformer"),
        FlipHorizontalTransformer("FlipHorizontalTransformer"),
        FlipVerticalTransformer("FlipVerticalTransformer"),
        ForegroundToBackgroundTransformer("ForegroundToBackgroundTransformer"),
        RotateDownTransformer("RotateDownTransformer"),
        RotateUpTransformer("RotateUpTransformer"),
        StackTransformer("StackTransformer"),
        TabletTransformer("TabletTransformer"),
        ZoomInTransformer("ZoomInTransformer"),
        ZoomOutSlideTransformer("ZoomOutSlideTransformer"),
        ZoomOutTranformer("ZoomOutTranformer");

        private final String className;

        // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
        Transformer(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }
    }
}