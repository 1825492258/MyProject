package com.xie.app.enforce.util.http.impl;


import com.xie.app.enforce.util.http.IResponse;

/**
 * Created by wuzongjie on 2017/10/28.
 * 请求返回的数据
 */

public class BaseResponse implements IResponse {
    private int code; // 数据返回的状态码 200，401
    private String data; // 接口数据

    public BaseResponse(int code, String data) {
        this.code = code;
        this.data = data;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }
}
