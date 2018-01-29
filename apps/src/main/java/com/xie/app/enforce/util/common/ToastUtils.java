package com.xie.app.enforce.util.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.xie.app.enforce.view.application.MyApplication;


/**
 * Created by Jie on 2018/1/4.
 * 基本工具类
 */

public class ToastUtils {

    private static Toast mToast; // 吐司

    /**
     * 定义吐司
     *
     * @param msg 吐司信息
     */
    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), "",
                    Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 判断是否有指定的权限
     *
     * @param activity    上下文
     * @param permissions 权限
     * @return 是否有权限
     */
    public static boolean hasPermission(AppCompatActivity activity, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请指定的权限
     *
     * @param activity    上下文
     * @param code        code
     * @param permissions 权限
     */
    public static void requestPermission(AppCompatActivity activity, int code, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            activity.requestPermissions(permissions, code);
        }
    }

    /**
     * 判断GPS是否打开
     *
     * @return true为打开
     */
    public static boolean isOpGPS() {
        LocationManager locationManager = (LocationManager) MyApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
