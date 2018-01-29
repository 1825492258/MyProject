package com.xie.app.enforce.view.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Jie on 2018/1/22.
 * Activity 的基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    private final static List<BaseActivity> mActivity = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivity) {
            mActivity.add(this);
        }
        // 得到界面ID并设置到Activity界面中
        int layId = getContentLayoutId();
        setContentView(layId);
        initView();
        initListener();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivity) {
            mActivity.remove(this);
        }
    }

    /**
     * 初始化控件
     */
    protected void initView() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件Id
     */
    protected abstract int getContentLayoutId();

    /**
     * 结束所有的Activity，退出时使用
     */
    public void killAll() {
        // 复制了一份mActivities 集合
        List<BaseActivity> copy;
        synchronized (mActivity) {
            copy = new LinkedList<>(mActivity);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }
}
