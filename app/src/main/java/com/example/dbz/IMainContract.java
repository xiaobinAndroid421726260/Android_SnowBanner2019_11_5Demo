package com.example.dbz;

import com.example.dbz.base.BasePresenter;
import com.example.dbz.base.BaseView;

import java.util.List;

/**
 * description:
 *
 * @author Db_z
 * date 2019/11/8 13:46
 * @version V1.0
 */
public interface IMainContract {

    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTasksList();

        void refreshData(List<String> data);

        void initBannerData(List<String> data);
    }

    interface Presenter extends BasePresenter {

        void startFail();

        List<String> getTransformerData();

        boolean isDataMissing();
    }
}
