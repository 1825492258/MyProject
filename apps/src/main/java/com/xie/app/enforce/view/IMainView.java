package com.xie.app.enforce.view;

import com.xie.app.enforce.model.bean.BikeBean;
import com.xie.app.enforce.model.bean.Users;

import java.util.List;

/**
 * Created by Jie on 2018/1/25.
 *
 */

public interface IMainView {
    /**
     * 返回车辆的信息
     * @param bikeBeans BikeBean
     */
    void getBike(List<BikeBean> bikeBeans);

    /**
     * 获取运营人员的信息
     * @param users Users
     */
    void getUser(List<Users> users);

    /**
     * 获取单车失败
     */
    void getBikeFail();

    /**
     * 获取运营人员失败
     */
    void getUserFail();

    /**
     * 显示Loading
     */
    void showLoading();

    /**
     * 隐藏弹窗
     */
    void hideLoading();
}
