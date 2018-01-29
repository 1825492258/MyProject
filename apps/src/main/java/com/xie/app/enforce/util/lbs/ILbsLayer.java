package com.xie.app.enforce.util.lbs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Jie on 2018/1/9.
 * 定义地图通用抽象接口
 */

public interface ILbsLayer {
    /**
     * 获取地图控件
     *
     * @return MapView
     */
    View getMapView();

    /**
     * 开始定位
     */
    void getStartLocationMap();

    /**
     * 结束定位
     */
    void getStopLocationMap();

    /**
     * 在地图上添加一个Marker
     *
     * @param locationInfo 位置信息
     * @param bitmap       图片
     * @param isLoad       是否展示动画
     */
    void addOnUpdateMarker(LocationInfo locationInfo, Bitmap bitmap, boolean isLoad);

    /**
     * 屏幕中心Marker跳动的动画
     */
    void startJumpAnimation();

    /**
     * 计算2点之间的直线距离
     *
     * @param locationInfo  第一个点
     * @param locationInfo1 第二个点
     * @return float
     */
    float getTwoDistance(LocationInfo locationInfo, LocationInfo locationInfo1);

    /**
     * 清除地图上所有的Marker
     */
    void clearAllMarkers();

    /**
     * 地图信息的返回
     *
     * @param changeListener CommonLocationChangeListener
     */
    void setLocationChangeListener(CommonLocationChangeListener changeListener);

    /**
     * 绘制2点之间的行车路径
     *
     * @param start    开始
     * @param end      结束
     * @param color    颜色
     * @param listener 回调
     */
    void driverRoute(LocationInfo start,
                     LocationInfo end,
                     int color,
                     OnRouteCompleteListener listener);

    /**
     * 地图位置变化
     */
    interface CommonLocationChangeListener {
        void onLocationChanged(LocationInfo locationInfo);

        // void onLocation(LocationInfo locationInfo);
    }

    /**
     * 地图中心发生变化
     */
    interface OnMapChangeListener {

        void onChangeFinish(LocationInfo result);

        void onChange();
    }

    /**
     * Marker 被点击
     */
    interface OnMapMarkerClickListener {
        void onMarkerClick(LocationInfo info);
    }

    /**
     * 地图被点击
     */
    interface OnMapIsClickListener {
        void onMapClick();
    }

    /**
     * 路径规划完成监听
     */
    interface OnRouteCompleteListener {
        void onComplete(RouteInfo result);
    }

    /**
     * 在地图中心添加一个Marker
     */
    void addCenterMarker(Bitmap bitmap);

    /**
     * 移除地图中心点
     */
    void removeCenterMarker();

    /**
     * 社管会高德地图的缩放比例
     *
     * @param zoom 比例
     */
    void setMapZoom(float zoom);

    /**
     * 地图中心点发生变化
     *
     * @param listener 中心变化的监听
     */
    void setMapCenterChange(OnMapChangeListener listener);

    /**
     * Marker 被点击
     *
     * @param listener OnMapMarkerClickListener
     */
    void setMarkerClick(OnMapMarkerClickListener listener);

    /**
     * Map被点击
     *
     * @param listener OnMapIsClickListener
     */
    void setMapOnClick(OnMapIsClickListener listener);

    /**
     * 移动相机到某个点
     *
     * @param locationInfo 地址
     * @param scale        缩放系数
     */
    void moveCameraToPoint(LocationInfo locationInfo, int scale);

    /**
     * 地图移动到2点之间的视野范围
     *
     * @param locationInfo1 坐标点1
     * @param locationInfo2 坐标点2
     */
    void moveCameraToTwoPoint(LocationInfo locationInfo1, LocationInfo locationInfo2);

    /**
     * 地图的生命周期函数
     */
    void onCreate(Bundle bundle);

    void onResume();

    void onPause();

    /**
     * 界面销毁
     */
    void onDestroy();
}
