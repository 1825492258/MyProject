package com.xie.app.enforce.util.listener;


import com.xie.app.enforce.util.http.impl.BaseResponse;

/**
 * Created by Jie on 2018/1/6.
 * MVP模式回调
 */

public interface ModelCallListener {

    void onSuccess(BaseResponse response);

    void onFailure(int code);
}
