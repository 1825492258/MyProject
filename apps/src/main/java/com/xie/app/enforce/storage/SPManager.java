package com.xie.app.enforce.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.xie.app.enforce.view.application.MyApplication;


/**
 * Created by Jie on 2018/1/6.
 * SharedPreferences 的封装 用来存储本地数据
 */

public class SPManager {

    private static SPManager spManager = null;
    private static SharedPreferences sp = null;
    private static SharedPreferences.Editor editor = null;

    @SuppressLint("CommitPrefEdits")
    private SPManager() {
        sp = MyApplication.getInstance().getSharedPreferences("", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SPManager getInstance() {
        if (spManager == null || sp == null || editor == null) {
            spManager = new SPManager();
        }
        return spManager;
    }

    /**
     * 保存 k - v
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 读取 k - v
     */
    public String getString(String key) {
        return sp.getString(key, null);
    }
}
