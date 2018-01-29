package com.xie.app.enforce.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xie.app.enforce.model.IMainModel;
import com.xie.app.enforce.model.bean.BikeBean;
import com.xie.app.enforce.model.bean.Users;
import com.xie.app.enforce.model.impl.MainModelImpl;
import com.xie.app.enforce.presenter.IMainPresenter;
import com.xie.app.enforce.util.http.impl.BaseResponse;
import com.xie.app.enforce.util.lbs.LocationInfo;
import com.xie.app.enforce.util.listener.ModelCallListener;
import com.xie.app.enforce.view.IMainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jie on 2018/1/25.
 * MainPresenterImpl
 */

public class MainPresenterImpl implements IMainPresenter {

    private IMainView mView;
    private IMainModel mModel;
    private List<BikeBean> bikeBeans;
    private List<Users> userBeans;

    public MainPresenterImpl(IMainView view) {
        this.mView = view;
        this.mModel = new MainModelImpl();
        this.bikeBeans = new ArrayList<>();
        this.userBeans = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    /**
     * 获取车辆
     *
     * @param info 参数 经纬度
     */
    @Override
    public void getHttpBike(LocationInfo info) {
        if (mView == null) return;
        // mView.showLoading();
        bikeBeans.clear();
        mView.hideLoading();
        for (int i = 0; i < 8; i++) {
            bikeBeans.add(new BikeBean("22" + i, info.getLongitude() - new Random().nextInt(16) * 0.0008, info.getLatitude() + new Random().nextInt(10) * 0.0008));
        }
        for (int i = 0; i < 6; i++) {
            bikeBeans.add(new BikeBean("33" + i, info.getLongitude() + new Random().nextInt(15) * 0.0008, info.getLatitude() - new Random().nextInt(12) * 0.0007));
        }
        for (int i = 0; i < 8; i++) {
            bikeBeans.add(new BikeBean("44" + i, info.getLongitude() + new Random().nextInt(10) * 0.0008, info.getLatitude() + new Random().nextInt(15) * 0.0006));
        }
        for (int i = 0; i < 8; i++) {
            bikeBeans.add(new BikeBean("55" + i, info.getLongitude() - new Random().nextInt(12) * 0.0004, info.getLatitude() - new Random().nextInt(10) * 0.0005));
        }
        mView.getBike(bikeBeans);
       /* mModel.getBike(info, new ModelCallListener() {
            @Override
            public void onSuccess(BaseResponse response) {
                Log.d("jiejie", "------" + response.getData());
                if (mView == null) return;
                mView.hideLoading();
                JsonObject object = new JsonParser().parse(response.getData()).getAsJsonObject();
                String status = object.getAsJsonPrimitive("status").getAsString();
                if ("SUCCESS".equals(status)) {
                    JsonArray array = object.getAsJsonArray("data");
                    bikeBeans.clear();
                    for (JsonElement obj : array) {
                        bikeBeans.add(new Gson().fromJson(obj, BikeBean.class));
                    }
                }
                mView.getBike(bikeBeans);
            }

            @Override
            public void onFailure(int code) {
                Log.d("jiejie", "------" + code);
                if (mView == null) return;
                mView.getBikeFail();
                mView.hideLoading();
            }
        });*/
    }

    /**
     * 获取运营人员
     *
     * @param info LocationInfo
     */
    @Override
    public void getHttpUser(LocationInfo info) {
        if (mView == null) return;
        mView.showLoading();
        userBeans.clear();
        userBeans.add(new Users("11111111111",info.getLongitude() + new Random().nextInt(10) * 0.0008, info.getLatitude() - new Random().nextInt(10) * 0.0008));
        userBeans.add(new Users("22222222222",info.getLongitude() + new Random().nextInt(10) * 0.0008, info.getLatitude() - new Random().nextInt(10) * 0.0008));
        mView.getUser(userBeans);
       /* mModel.getUser(info, new ModelCallListener() {
            @Override
            public void onSuccess(BaseResponse response) {
                Log.d("jiejie", "-----user----" + response.getData());
                if (mView == null) return;
                JsonObject object = new JsonParser().parse(response.getData()).getAsJsonObject();
                String status = object.getAsJsonPrimitive("status").getAsString();
                if ("SUCCESS".equals(status)) {
                    JsonArray array = object.getAsJsonArray("data");
                    userBeans.clear();
                    for (JsonElement obj : array) {
                        userBeans.add(new Gson().fromJson(obj, Users.class));
                    }
                }
                mView.getUser(userBeans);
            }

            @Override
            public void onFailure(int code) {
                Log.d("jiejie", "-------user----" + code);
                if (mView == null) return;
                mView.getUserFail();
            }
        });*/
    }

}
