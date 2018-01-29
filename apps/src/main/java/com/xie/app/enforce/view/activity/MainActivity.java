package com.xie.app.enforce.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.xie.app.enforce.R;
import com.xie.app.enforce.model.bean.BikeBean;
import com.xie.app.enforce.model.bean.Users;
import com.xie.app.enforce.presenter.IMainPresenter;
import com.xie.app.enforce.presenter.impl.MainPresenterImpl;
import com.xie.app.enforce.util.common.StatusBarUtil;
import com.xie.app.enforce.util.common.ToastUtils;
import com.xie.app.enforce.util.lbs.ILbsLayer;
import com.xie.app.enforce.util.lbs.LocationInfo;
import com.xie.app.enforce.util.lbs.MapLbsLayerImpl;
import com.xie.app.enforce.util.lbs.RouteInfo;
import com.xie.app.enforce.util.view.ToolBarHelp;
import com.xie.app.enforce.view.IMainView;
import com.xie.app.enforce.view.activity.base.BaseActivity;
import com.xie.app.enforce.view.fragment.LeftFragment;

import java.util.List;

import butterknife.BindView;

/**
 * 主界面
 * 主要有地图定位获取信息
 * 侧滑以及去扫码的界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, ILbsLayer.CommonLocationChangeListener, ILbsLayer.OnMapChangeListener, IMainView, ILbsLayer.OnMapMarkerClickListener, ILbsLayer.OnMapIsClickListener, ILbsLayer.OnRouteCompleteListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout; // 侧滑的控件
    @BindView(R.id.btn_main_scan)
    Button mScanButton; // 进入扫一扫的界面
    @BindView(R.id.btn_position)
    Button mLocationButton; // 回到当前的位置
    @BindView(R.id.loading_progress)
    ProgressBar mLoadProgress; // 加载时的弹窗
    @BindView(R.id.map_container)
    FrameLayout mMapLayout; // 地图界面
    private ToolBarHelp mBarHelp; // 状态栏
    private ILbsLayer mLbsLayer; // 地图控制
    private LocationInfo mLocationInfo; // 定位的坐标
    private Bitmap mLocationBit; // 定位的坐标
    private LocationInfo mCenterLocation; // 地图中心点的坐标
    private Bitmap mCenterBit; // 地图中心的图标
    private Bitmap mBikeBit; // 正常车辆的图标
    private Bitmap mUserBit; // 运营人员的坐标
    private boolean isFirst;
    private IMainPresenter mMainPresenter; // 首页的Presenter
    private List<BikeBean> bikeBeans;
    private List<Users> users;
    private LocationInfo mStartInfo; // 画轨迹时的开始坐标
    private int type;

    @Override
    protected void initListener() {
        super.initListener();
        mBarHelp.setLeftListener(this);
        mScanButton.setOnClickListener(this);
        mLocationButton.setOnClickListener(this);
        // 定位返回的监听
        mLbsLayer.setLocationChangeListener(this);
        // 地图中心点发生变化的回调
        mLbsLayer.setMapCenterChange(this);
        // 地图上Marker点击的回调
        mLbsLayer.setMarkerClick(this);
        // 地图被点击时的回调
        mLbsLayer.setMapOnClick(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        mBarHelp = new ToolBarHelp(this);
        mBarHelp.setTitle(getString(R.string.app_name));
        mBarHelp.setLeftImage(R.drawable.ic_main_man);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mBarHelp.getToolbar());
        LeftFragment leftFragment = new LeftFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.left_content, leftFragment).commit();
        mLbsLayer = new MapLbsLayerImpl(this);
        mMapLayout.addView(mLbsLayer.getMapView());
        mMainPresenter = new MainPresenterImpl(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLbsLayer.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        initStartLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_left_layout: // 点击打开侧滑界面
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.btn_position: // 点击重新定位
                mLbsLayer.getStopLocationMap(); // 先结束上次定位
                initStartLocation();           // 重新开始定位
                break;
            case R.id.btn_main_scan: // 扫一扫
                if (ToastUtils.hasPermission(this, Manifest.permission.CAMERA)) {
                    Intent intent = new Intent(this, ScanCodeActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.requestPermission(this, 0x02, Manifest.permission.CAMERA);
                }
                break;
        }
    }

    /**
     * 高德地图定位
     * 定位需要动态权限的设置
     */
    private void initStartLocation() {
        if (ToastUtils.hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            mLbsLayer.getStartLocationMap();
        } else {
            ToastUtils.requestPermission(this, 0x01, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    /**
     * 位置发生变化  这里定位只进行一次定位
     */
    @Override
    public void onLocationChanged(LocationInfo locationInfo) {
        mLocationInfo = locationInfo;
        mLbsLayer.moveCameraToPoint(locationInfo, 15);
        addLocationMarker();
    }

    /**
     * 添加定位Marker
     */
    private void addLocationMarker() {
        if (mLocationInfo == null) return;
        if (mLocationBit == null || mLocationBit.isRecycled()) {
            mLocationBit = BitmapFactory.decodeResource(getResources(), R.drawable.navi_map_gps_locked);
        }
        mLbsLayer.addOnUpdateMarker(mLocationInfo, mLocationBit, false);
    }

    /**
     * 添加车辆Marker
     */
    private void addBikeMarker(BikeBean bean,boolean isLoad) {
        if (mBikeBit == null || mBikeBit.isRecycled()) {
            mBikeBit = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bike);
        }
        mLbsLayer.addOnUpdateMarker(new LocationInfo(bean.getVehicleNumber(), bean.getLat(), bean.getLng(), 1), mBikeBit, isLoad);
    }

    /**
     * 添加自行车的Marker
     *
     * @param bean TrailBean
     */
    private void addUserMarker(Users bean,boolean isLoad) {
        if (mUserBit == null || mUserBit.isRecycled()) {
            mUserBit = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bike_user);
        }
        mLbsLayer.addOnUpdateMarker(new LocationInfo(bean.getPhoneNumber(), bean.getLat(), bean.getLng(), 2), mUserBit, isLoad);
    }

    /**
     * 添加高德地图中心点Marker
     */
    private void addCenterMarker() {
        if (mCenterBit == null || mCenterBit.isRecycled()) {
            mCenterBit = BitmapFactory.decodeResource(getResources(), R.drawable.ic_position);
        }
        mLbsLayer.addCenterMarker(mCenterBit);
    }

    private void addCenterMarkers() {
        if (mStartInfo == null) return;
        if (mCenterBit == null || mCenterBit.isRecycled()) {
            mCenterBit = BitmapFactory.decodeResource(getResources(), R.drawable.ic_position);
        }
        mLbsLayer.addOnUpdateMarker(mStartInfo, mCenterBit, false);
    }

    /**
     * 改变地图上的Marker 状态
     */
    private void changeMapMarker(boolean isLoad) {
        mLbsLayer.clearAllMarkers();
        type = 0;
        addLocationMarker();
        for (BikeBean bean : bikeBeans) {
            addBikeMarker(bean,isLoad);
        }
        for (Users user : users) {
            addUserMarker(user,isLoad);
        }
        addCenterMarker();
        mLbsLayer.startJumpAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLbsLayer.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLbsLayer.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLbsLayer.onDestroy();
        mMainPresenter.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x01: // 请求定位权限的返回
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLbsLayer.getStartLocationMap();
                } else {
                    ToastUtils.showToast("系统定位权限被拒");
                }
                break;
            case 0x02: // 打开相机的权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, ScanCodeActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.showToast("打开摄像头权限被拒");
                }
                break;
        }
    }

    /**
     * 地图中心移动后
     */
    @Override
    public void onChangeFinish(LocationInfo result) {
        mStartInfo = result;
        if (!isFirst) {
            isFirst = true;
            mCenterLocation = result;
            addCenterMarker();
            mMainPresenter.getHttpUser(result);
        }
        float distance = mLbsLayer.getTwoDistance(mCenterLocation, result);
        if (distance > 1800) {
            mCenterLocation = result;
            mMainPresenter.getHttpUser(mCenterLocation);
        }
    }

    /**
     * 地图中心移动的过程
     */
    @Override
    public void onChange() {

    }

    /**
     * 获取附近的车辆
     *
     * @param bikeBeans BikeBean
     */
    @Override
    public void getBike(List<BikeBean> bikeBeans) {
        this.bikeBeans = bikeBeans;
        changeMapMarker(true);
    }

    /**
     * 获取运营人员
     *
     * @param users Users
     */
    @Override
    public void getUser(List<Users> users) {
        this.users = users;
        mMainPresenter.getHttpBike(mCenterLocation);
    }

    /**
     * 获取车辆失败
     */
    @Override
    public void getBikeFail() {

    }

    @Override
    public void getUserFail() {
        mMainPresenter.getHttpBike(mCenterLocation);
    }

    /**
     * 展示弹窗
     */
    @Override
    public void showLoading() {
        mLoadProgress.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏弹窗
     */
    @Override
    public void hideLoading() {
        mLoadProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadProgress.setVisibility(View.INVISIBLE);
            }
        }, 800);
    }

    /**
     * Marker 被点击
     *
     * @param info LocationInfo
     */
    @Override
    public void onMarkerClick(LocationInfo info) {
        if (info.getType() == 1) {
            type = 1;
            mLbsLayer.removeCenterMarker();
            addCenterMarkers();
            mLbsLayer.driverRoute(info, mStartInfo, Color.RED, this);
            mLbsLayer.moveCameraToTwoPoint(mStartInfo, info);
        } else if (info.getType() == 2) {
            ToastUtils.showToast(info.getLatitude() + "  " + info.getLongitude());
        }
    }

    /**
     * Map 被点击
     */
    @Override
    public void onMapClick() {
        if (type == 1) {
            mLbsLayer.setMapZoom(14);
            changeMapMarker(false);
        }
    }

    /**
     * @param result RouteInfo
     */
    @Override
    public void onComplete(RouteInfo result) {
        // 返回所需的时间和路长
        Log.d("jiejie", "-----" + result.getDistance() + "   " + result.getDuration());
    }
}
