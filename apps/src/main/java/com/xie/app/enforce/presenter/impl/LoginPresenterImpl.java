package com.xie.app.enforce.presenter.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xie.app.enforce.R;
import com.xie.app.enforce.model.ILoginModel;
import com.xie.app.enforce.presenter.ILoginPresenter;
import com.xie.app.enforce.storage.SPManager;
import com.xie.app.enforce.util.common.HttpUrlConstant;
import com.xie.app.enforce.util.http.impl.BaseResponse;
import com.xie.app.enforce.util.listener.ModelCallListener;
import com.xie.app.enforce.view.ILoginView;
import com.xie.app.enforce.view.application.MyApplication;


/**
 * Created by Jie on 2018/1/6.
 * 登录的Presenter的实现类
 */

public class LoginPresenterImpl implements ILoginPresenter, ModelCallListener {

    private ILoginView mView;
    private ILoginModel mModel;
    private String name, password;

    public LoginPresenterImpl(ILoginView view, ILoginModel model) {
        this.mView = view;
        this.mModel = model;
    }

    @Override
    public void requestLogin(String name, String passWord) {
        if (mView != null) {
            mView.showLoading();
        }
        this.name = name;
        this.password = passWord;
        mModel.login(name, passWord, this);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onSuccess(BaseResponse response) {
        if (mView == null) return;
        mView.hideLoading();
        JsonObject object = new JsonParser().parse(response.getData()).getAsJsonObject();
        String status = object.getAsJsonPrimitive("status").getAsString();
        String error = object.getAsJsonPrimitive("error").getAsString();
        if ("SUCCESS".equals(status)) {
            SPManager.getInstance().putString("name", name);
            SPManager.getInstance().putString("password", password);
            HttpUrlConstant.HEAD = object.getAsJsonObject("data").getAsJsonPrimitive(HttpUrlConstant.AUTHORIZATION).getAsString();
            mView.loginSuccess();
        } else {
            mView.loginFail(error);
        }
    }

    @Override
    public void onFailure(int code) {
        if (mView == null) return;
        mView.hideLoading();
        mView.loginFail(MyApplication.getInstance().getString(R.string.login_fail));
    }
}
