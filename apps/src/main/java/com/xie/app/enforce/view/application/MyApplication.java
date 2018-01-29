package com.xie.app.enforce.view.application;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by Jie on 2018/1/22.
 * 基类
 */

public class MyApplication extends Application {

    private static MyApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        ZXingLibrary.initDisplayOpinion(this);
    }

    public static MyApplication getInstance() {
        return mApplication;
    }
}
