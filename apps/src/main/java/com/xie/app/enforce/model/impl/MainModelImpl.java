package com.xie.app.enforce.model.impl;

import com.xie.app.enforce.model.IMainModel;
import com.xie.app.enforce.util.common.HttpUrlConstant;
import com.xie.app.enforce.util.http.IHttpClient;
import com.xie.app.enforce.util.http.IRequest;
import com.xie.app.enforce.util.http.impl.BaseRequest;
import com.xie.app.enforce.util.http.impl.BaseResponse;
import com.xie.app.enforce.util.http.impl.OKHttpClientImp;
import com.xie.app.enforce.util.lbs.LocationInfo;
import com.xie.app.enforce.util.listener.ModelCallListener;

/**
 * Created by Jie on 2018/1/25.
 * MainModelImpl
 */

public class MainModelImpl implements IMainModel {
    /**
     * 请求单车的接口
     *
     * @param info     传入的信息
     * @param listener 回调接口
     */
    @Override
    public void getBike(LocationInfo info, final ModelCallListener listener) {
        IRequest request = new BaseRequest(HttpUrlConstant.BIKE_URL);
        request.setHeader(HttpUrlConstant.AUTHORIZATION, HttpUrlConstant.HEAD);
        request.setBody("lng", info.getLongitude());
        request.setBody("lat", info.getLatitude());
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

    /**
     * 请求运营人员
     *
     * @param info     传入的信息
     * @param listener 回调接口
     */
    @Override
    public void getUser(LocationInfo info, final ModelCallListener listener) {
        IRequest request = new BaseRequest(HttpUrlConstant.USERS_URL);
        request.setHeader(HttpUrlConstant.AUTHORIZATION, HttpUrlConstant.HEAD);
        request.setBody("lng", info.getLongitude());
        request.setBody("lat", info.getLatitude());
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
