package com.xie.app.enforce.presenter;

import com.xie.app.enforce.util.lbs.LocationInfo;

/**
 * Created by Jie on 2018/1/25.
 * IMainPresenter
 */

public interface IMainPresenter {
    /**
     * 销毁
     */
    void onDestroy();

    /**
     * 获取附近的单车
     * @param info 参数
     */
    void getHttpBike(LocationInfo info);

    /**
     * 获取附近的运营人员
     * @param info 参数
     */
    void getHttpUser(LocationInfo info);
}
