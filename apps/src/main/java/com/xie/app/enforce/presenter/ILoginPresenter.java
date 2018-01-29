package com.xie.app.enforce.presenter;

/**
 * Created by Jie on 2018/1/6.
 * 登录
 */

public interface ILoginPresenter {
    /**
     * 登录
     *
     * @param name     用户名
     * @param passWord 密码
     */
    void requestLogin(String name, String passWord);

    /**
     * 销毁
     */
    void onDestroy();
}
