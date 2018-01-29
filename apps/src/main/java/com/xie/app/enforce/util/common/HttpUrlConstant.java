package com.xie.app.enforce.util.common;

/**
 * Created by Jie on 2018/1/6.
 * 接口请求地址以及接口中的常用静态常量
 */

public class HttpUrlConstant {
    public final static String AUTHORIZATION = "Authorization";
    public static String HEAD;

    /*
    * 外网服务器地址
    */
    private final static String SERVER_URL = "http://118.31.228.237:8086/v1/";

    /**
     * 登录接口
     * Authorization
     */
    public final static String LOGIN_URL = SERVER_URL + "app-user-login";

    /**
     * 首页获取车辆的信息
     * get
     * lng
     */
    public final static String BIKE_URL = SERVER_URL + "vehicle-trail/range-vehicles?lng={lng}&lat={lat}";

    /**
     * 首页获取城管的信息
     * get
     * lng : 给定的经度
     * lat : 给定的纬度
     */
    public final static String USERS_URL = SERVER_URL + "maintain-users/ranges?lng={lng}&lat={lat}";
}
