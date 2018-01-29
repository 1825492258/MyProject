package com.xie.app.enforce.model.impl;

import com.xie.app.enforce.model.ILoginModel;
import com.xie.app.enforce.util.common.HttpUrlConstant;
import com.xie.app.enforce.util.http.IHttpClient;
import com.xie.app.enforce.util.http.IRequest;
import com.xie.app.enforce.util.http.impl.BaseRequest;
import com.xie.app.enforce.util.http.impl.BaseResponse;
import com.xie.app.enforce.util.http.impl.OKHttpClientImp;
import com.xie.app.enforce.util.listener.ModelCallListener;

import okhttp3.Credentials;

/**
 * Created by Jie on 2018/1/6.
 * 登录的Model
 */

public class LoginModelImpl implements ILoginModel {

    @Override
    public void login(String name, String password, final ModelCallListener listener) {
        String basic = Credentials.basic(name, password);
        IRequest request = new BaseRequest(HttpUrlConstant.LOGIN_URL);
        request.setHeader(HttpUrlConstant.AUTHORIZATION, basic);
        OKHttpClientImp.getInstance().get(request, false, new IHttpClient.RequestCallBack() {
            @Override
            public void onSuccess(BaseResponse response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(int code) {
                listener.onFailure(code);
            }
        });
    }
}
