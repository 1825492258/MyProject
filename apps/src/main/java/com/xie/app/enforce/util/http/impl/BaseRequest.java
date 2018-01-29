package com.xie.app.enforce.util.http.impl;



import com.google.gson.Gson;
import com.xie.app.enforce.util.http.IRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzongjie on 2017/10/28.
 * 封装参数的实现
 */

public class BaseRequest implements IRequest {

    private String method = "POST";
    private String url; // 请求地址
    private Map<String, String> header; // 请求头
    private Map<String, Object> body; // 请求体

    public BaseRequest(String url) {
        /*
         * 公共参数及头部信息
         */
        this.url = url;
        header = new HashMap<>();
        body = new HashMap<>();
        // header.put("xx","xx"); 可以设置固定的头部
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setHeader(String key, String value) {
        header.put(key, value);
    }

    @Override
    public void setBody(String key, Object value) {
        body.put(key, value);
    }

    @Override
    public String getUrl() {
        if ("GET".equals(method)) {
            // 组装GET请求参数
            for (String key : body.keySet()) {
                url = url.replace("{" + key + "}", body.get(key).toString());
            }
        }
        return url;
    }

    @Override
    public Map<String, String> getHeader() {
        return header;
    }

    @Override
    public Object getBody() {
        if (body != null) {
            if (this.body.keySet().iterator().next().equals("null")) {
                return new Gson().toJson(this.body.get("null"), ArrayList.class);
            } else {
                return new Gson().toJson(this.body, HashMap.class);
            }
        } else {
            return "{}";
        }
    }
}
