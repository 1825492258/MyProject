package com.xie.app.enforce.util.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.xie.app.enforce.R;


/**
 * Created by Jie on 2018/1/6.
 * 登录的弹窗
 */

public class LoadDialog extends Dialog {
    public LoadDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load);
    }
}
