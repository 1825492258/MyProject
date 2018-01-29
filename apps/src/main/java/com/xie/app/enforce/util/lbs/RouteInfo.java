package com.xie.app.enforce.util.lbs;

/**
 * Created by Jie on 2018/1/25.
 * 路径规划返回
 */

public class RouteInfo {

    private float distance; // 距离
    private int duration;   // 时长

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
