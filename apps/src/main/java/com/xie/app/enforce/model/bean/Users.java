package com.xie.app.enforce.model.bean;

/**
 * Created by Jie on 2017/12/23.
 * 首页获取的管理员
 */

public class Users {

    private String name; // 名称
    private String phoneNumber; // 号码
    private String operator; // 运营方
    private long createTime; // 时间
    private double lng; // 经纬度
    private double lat;

    public Users(String phoneNumber, double lng, double lat) {
        this.phoneNumber = phoneNumber;
        this.lng = lng;
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
