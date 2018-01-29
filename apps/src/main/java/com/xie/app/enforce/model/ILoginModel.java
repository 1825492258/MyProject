package com.xie.app.enforce.model;


import com.xie.app.enforce.util.listener.ModelCallListener;

/**
 * Created by Jie on 2018/1/6.
 * 登录的Model
 */

public interface ILoginModel {
    /**
     * 登录
     *
     * @param name     用户名
     * @param password 密码
     * @param listener 回调
     */
    void login(String name, String password, ModelCallListener listener);

}
