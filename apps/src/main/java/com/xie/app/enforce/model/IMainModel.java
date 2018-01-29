package com.xie.app.enforce.model;

import com.xie.app.enforce.util.lbs.LocationInfo;
import com.xie.app.enforce.util.listener.ModelCallListener;

/**
 * Created by Jie on 2018/1/25.
 * 首页的Model
 */

public interface IMainModel {
    /**
     * 获取单车
     *
     * @param info     信息
     * @param listener 回调
     */
    void getBike(LocationInfo info, ModelCallListener listener);

    /**
     * 获取运营人员
     *
     * @param info     信息
     * @param listener 回调
     */
    void getUser(LocationInfo info, ModelCallListener listener);
}
