package com.xie.app.enforce.view;

/**
 * Created by Jie on 2018/1/6.
 *
 */

public interface ILoginView {

    /**
     * 显示Loading
     */
    void showLoading();

    /**
     * 隐藏弹窗
     */
    void hideLoading();

    /**
     * 登录
     */
    void loginSuccess();

    /**
     * 登录失败
     * @param message 信息
     */
    void loginFail(String message);

}
