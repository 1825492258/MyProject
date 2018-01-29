package com.xie.app.enforce.util.lbs;

/**
 * Created by Jie on 2018/1/9.
 * 自定义Bean 定位返回的数据
 */

public class LocationInfo {

    private String key; // 唯一标示
    private double latitude; // 经度
    private double longitude; // 纬度
    private int type; // 加个类型做区分

    public LocationInfo(String key, double latitude, double longitude, int type) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocationInfo(String key, double latitude, double longitude) {
        this.key = key;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationInfo(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
