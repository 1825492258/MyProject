package com.xie.app.enforce.model.bean;

/**
 * Created by Jie on 2018/1/17.
 * 首页返回的车辆接口
 */

public class BikeBean {

    private String id;
    private String vehicleNumber; // 车辆编号
    private long updateTime; // 更新时间
    private int routeId; // routeId
    private double lng; // 经度
    private double lat; // 纬度
    private int status; // 状态 0.开锁 1.运动 2.关锁

    public BikeBean(String vehicleNumber, double lng, double lat) {
        this.vehicleNumber = vehicleNumber;
        this.lng = lng;
        this.lat = lat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
