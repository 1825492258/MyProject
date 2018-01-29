package com.xie.app.enforce.util.lbs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.xie.app.enforce.util.common.DensityUtils;
import com.xie.app.enforce.util.common.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jie on 2018/1/9.
 * MapView 的实现类
 */

public class MapLbsLayerImpl implements ILbsLayer, AMap.OnCameraChangeListener, AMap.OnMapClickListener {

    private Context mContext;
    private MapView mapView; // 地图视图对象
    private AMap aMap; // 地图管理对象
    private AMapLocationClient mLocationClient; // 申明对象
    private AMapLocationClientOption mLocationOption = null; // 声明LocationOption对象
    private LocationSource.OnLocationChangedListener mListener;
    private CommonLocationChangeListener mLocationChangeListener; // 定位位置变化
    private OnMapChangeListener mChangeListener; // 地图中心变化监听
    private RouteSearch mRouteSearch; // 路径查询对象
    private OnMapMarkerClickListener mMarkerListener; // 地图上的Marker被点击
    private OnMapIsClickListener mMapIsClick; // 地图被点击
    private boolean isLoadCenter; // 地图中心移动的回调是否返回

    public MapLbsLayerImpl(Context context) {
        this.mContext = context;
        mapView = new MapView(mContext);
        isLoadCenter = false;
        if (aMap == null) aMap = mapView.getMap();
        // 隐藏高德地图右下角缩放的按钮
        aMap.getUiSettings().setZoomControlsEnabled(false);
        // 设置Marker点击，地图不移动到中心点
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (mMarkerListener != null) {
                    LocationInfo info = (LocationInfo) marker.getObject();
                    mMarkerListener.onMarkerClick(info);
                }
                return true;
            }
        });
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMapClickListener(this);
    }

    /**
     * 地图控件
     *
     * @return View
     */
    @Override
    public View getMapView() {
        return mapView;
    }

    /**
     * 停止定位
     */
    @Override
    public void getStopLocationMap() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    /**
     * 开始定位
     */
    @Override
    public void getStartLocationMap() {
        // 设置定位监听
        aMap.setLocationSource(new LocationSource() {
            /**
             * 激活定位
             * @param onLocationChangedListener OnLocationChangedListener
             */
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                mListener = onLocationChangedListener;
                if (mLocationClient == null) {
                    // 初始化定位
                    mLocationClient = new AMapLocationClient(mContext);
                    // 初始化定位参数
                    mLocationOption = new AMapLocationClientOption();
                    // 设置定位回调监听
                    mLocationClient.setLocationListener(locationListener);
                    // 设置定位模式
                    if (ToastUtils.isOpGPS()) {
                        // 设置为高精度定位模式
                        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    } else {
                        // 设置为低精度定位模式
                        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
                    }
                    // mLocationOption.setInterval(6000); // 设置多久定位一次
                    mLocationOption.setOnceLocation(true);
                    // 设置定位参数
                    mLocationClient.setLocationOption(mLocationOption);
                    mLocationClient.startLocation(); // 启动定位
                }
            }

            /**
             * 停止定位
             */
            @Override
            public void deactivate() {
                mListener = null;
                if (mLocationClient != null) {
                    mLocationClient.stopLocation();
                    mLocationClient.onDestroy();
                }
                mLocationClient = null;
            }
        });
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
    }

    /**
     * 定位的监听
     */
    // private boolean isFirst = true;
    private AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (mListener != null && aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    LocationInfo info = new LocationInfo(aMapLocation.getLatitude(),
                            aMapLocation.getLongitude());
                    info.setKey("0000");
                    if (mLocationChangeListener != null) {
                        mLocationChangeListener.onLocationChanged(info);
                    }
                } else {
                    Log.d("jiejie", "定位失败" + aMapLocation.getErrorCode() + " " + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    private Map<String, Marker> markerMap = new HashMap<>(); // 管理地图标记的集合

    /**
     * 添加Marker
     *
     * @param locationInfo 位置信息
     * @param bitmap       图片
     * @param isLoad       是否展示动画
     */
    @Override
    public void addOnUpdateMarker(LocationInfo locationInfo, Bitmap bitmap, boolean isLoad) {
        Marker storeMarker = markerMap.get(locationInfo.getKey());
        LatLng latLng = new LatLng(locationInfo.getLatitude(), locationInfo.getLongitude());
        if (storeMarker != null) {
            // 如果已经存在则更新
            storeMarker.setPosition(latLng);
        } else {
            // 如果没有则创建
            MarkerOptions options = new MarkerOptions();
            BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bitmap);
            options.icon(des);
            options.anchor(0.5f, 0.5f);
            options.position(latLng);
            Marker marker = aMap.addMarker(options);
            marker.setObject(locationInfo); // 把相应的对象赋给Marker
            if (isLoad) startGrowAnimation(marker);
            markerMap.put(locationInfo.getKey(), marker);
        }
    }

    /**
     * 屏幕中心Marker跳动的动画
     */
    @Override
    public void startJumpAnimation() {
        if (mScreenMarker != null) {
            // 根据屏幕计算需要移动的目标点
            final LatLng latLng = mScreenMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= DensityUtils.dp2px(45);
            LatLng target = aMap.getProjection().fromScreenLocation(point);
            // 使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的Interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            // 整个移动所需要的时间
            animation.setDuration(500);
            // 设置动画
            mScreenMarker.setAnimation(animation);
            // 开始动画
            mScreenMarker.startAnimation();
        }
    }

    /**
     * 地上生长得Marker
     *
     * @param marker Marker
     */
    private void startGrowAnimation(Marker marker) {
        if (marker != null) {
            Animation animation = new ScaleAnimation(0, 1, 0, 1);
            animation.setInterpolator(new LinearInterpolator());
            // 整个移动所需要的时间
            animation.setDuration(600);
            // 设置动画
            marker.setAnimation(animation);
            // 开始动画
            marker.startAnimation();
        }
    }

    /**
     * 位置改变
     *
     * @param changeListener Change
     */
    @Override
    public void setLocationChangeListener(CommonLocationChangeListener changeListener) {
        this.mLocationChangeListener = changeListener;
    }

    /**
     * 2点之间路径规划
     *
     * @param start    开始
     * @param end      结束
     * @param color    颜色
     * @param listener 回调
     */
    @Override
    public void driverRoute(LocationInfo start, LocationInfo end, final int color, final OnRouteCompleteListener listener) {
        // 1 组装起点和终点信息
        LatLonPoint startLatLng =
                new LatLonPoint(start.getLatitude(), start.getLongitude());
        LatLonPoint endLatLng =
                new LatLonPoint(end.getLatitude(), end.getLongitude());
        RouteSearch.FromAndTo fromAndTo =
                new RouteSearch.FromAndTo(startLatLng, endLatLng);
        // 2 创建路径查询参数
        RouteSearch.WalkRouteQuery query =
                new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
        // 3 创建搜索对象，异步路径规划
        if (mRouteSearch == null) {
            mRouteSearch = new RouteSearch(mContext);
        }
        // 4 异步发送请求
        mRouteSearch.calculateWalkRouteAsyn(query);
        // 5 接受数据
        mRouteSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
                isLoadCenter = true;
                // 1.获取第一条路径
                WalkPath walkPath = walkRouteResult.getPaths().get(0);
                // 2.获取这条路径上的所有的点，使用Polyline 绘制路径
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(color);
                // 起点
                LatLonPoint startPoint = walkRouteResult.getStartPos();
                // 路径中间步奏
                List<WalkStep> walkSteps = walkPath.getSteps();
                // 路径终点
                LatLonPoint endPoint = walkRouteResult.getTargetPos();
                // 添加起点
                polylineOptions.add(new LatLng(startPoint.getLatitude(), startPoint.getLongitude()));
                // 添加中间节点
                for (WalkStep step : walkSteps) {
                    List<LatLonPoint> latLonPoints = step.getPolyline();
                    for (LatLonPoint latLonPoint : latLonPoints) {
                        LatLng latLng = new LatLng(latLonPoint.getLatitude(),
                                latLonPoint.getLongitude());
                        polylineOptions.add(latLng);
                    }
                }
                // 添加终点
                polylineOptions.add(new LatLng(endPoint.getLatitude(), endPoint.getLongitude()));
                if (mPolyline != null) {
                    mPolyline.remove();
                    mPolyline = null;
                }
                // 执行绘制
                mPolyline = aMap.addPolyline(polylineOptions);
                // 回调业务
                if (listener != null) {
                    RouteInfo info = new RouteInfo();
                    info.setDistance(0.5f + walkPath.getDistance() / 1000);
                    info.setDuration(10 + new Long(walkPath.getDuration() / 1000 * 60).intValue());
                    listener.onComplete(info);
                }
            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
    }

    private Polyline mPolyline = null;
    private Marker mScreenMarker; // 地图中心的Marker

    /**
     * 地图中心添加一个Marker
     */
    @Override
    public void addCenterMarker(Bitmap bitmap) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        if (mScreenMarker != null) {
            // 已存在 改变中心位置即可
            mScreenMarker.setPosition(latLng);
            // 设置Marker在屏幕上，不跟随地图的移动而移动
            mScreenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        } else {
            mScreenMarker = aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
            // 设置Marker在屏幕上，不跟随地图而移动
            mScreenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        }
    }

    @Override
    public void removeCenterMarker() {
        if (mScreenMarker != null) mScreenMarker.remove();
    }

    /**
     * 地图中心发生变化的监听
     *
     * @param listener 中心变化的监听
     */
    @Override
    public void setMapCenterChange(OnMapChangeListener listener) {
        this.mChangeListener = listener;
    }

    /**
     * Marker 点击事件
     *
     * @param listener OnMapMarkerClickListener
     */
    @Override
    public void setMarkerClick(OnMapMarkerClickListener listener) {
        this.mMarkerListener = listener;
    }

    /**
     * 地图点击事件
     *
     * @param listener OnMapIsClickListener
     */
    @Override
    public void setMapOnClick(OnMapIsClickListener listener) {
        this.mMapIsClick = listener;
    }

    /**
     * 移动地图中心到某个点
     *
     * @param locationInfo 地址
     * @param scale        缩放系数
     */
    @Override
    public void moveCameraToPoint(LocationInfo locationInfo, int scale) {
        LatLng latLng = new LatLng(locationInfo.getLatitude(), locationInfo.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(
                new CameraPosition(latLng, scale, 0, 0)
        );
        aMap.moveCamera(update);
    }

    /**
     * 地图移动到2点之间的视野范围
     *
     * @param locationInfo1 坐标点1
     * @param locationInfo2 坐标点2
     */
    @Override
    public void moveCameraToTwoPoint(LocationInfo locationInfo1, LocationInfo locationInfo2) {
        LatLng latLng1 = new LatLng(locationInfo1.getLatitude(), locationInfo1.getLongitude());
        LatLng latLng2 = new LatLng(locationInfo2.getLatitude(), locationInfo2.getLongitude());
        LatLngBounds.Builder b = LatLngBounds.builder();
        b.include(latLng1);
        b.include(latLng2);
        LatLngBounds latLngBounds = b.build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 300));
    }

    /**
     * 计算2点间直线距离
     *
     * @param locationInfo1 第一个点
     * @param locationInfo2 第二个点
     * @return float
     */
    @Override
    public float getTwoDistance(LocationInfo locationInfo1, LocationInfo locationInfo2) {
        LatLng latLng1 = new LatLng(locationInfo1.getLatitude(), locationInfo1.getLongitude());
        LatLng latLng2 = new LatLng(locationInfo2.getLatitude(), locationInfo2.getLongitude());
        return AMapUtils.calculateLineDistance(latLng1, latLng2);
    }

    @Override
    public void onCreate(Bundle bundle) {
        mapView.onCreate(bundle);
    }

    @Override
    public void onResume() {
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
    }

    /**
     * 销毁
     */
    @Override
    public void onDestroy() {
        mapView.onDestroy();
        getStopLocationMap();
    }

    /**
     * 清除地图上所有的Marker
     */
    @Override
    public void clearAllMarkers() {
        aMap.clear();
        markerMap.clear();
        mScreenMarker = null;
    }

    /**
     * 地图中心发生改变的过程中
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (isLoadCenter) return; // 如果不让回调就返回
        if (mChangeListener != null && cameraPosition != null) {
            mChangeListener.onChange();
        }
    }

    /**
     * 地图中心发生改变后
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (isLoadCenter) return; // 如果不让回调就返回
        if (mChangeListener != null && cameraPosition != null) {
            mChangeListener.onChangeFinish(new LocationInfo("1111", cameraPosition.target.latitude,
                    cameraPosition.target.longitude));
        }
    }

    /**
     * 设置缩放比例
     *
     * @param zoom 比例
     */
    @Override
    public void setMapZoom(float zoom) {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    /**
     * 高德地图被点击
     * @param latLng 返回被点击的坐标的信息
     */
    @Override
    public void onMapClick(LatLng latLng) {
        if (mMapIsClick != null) {
            isLoadCenter = false;
            mMapIsClick.onMapClick();
        }
    }
}
