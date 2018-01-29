package com.xie.app.enforce.util.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Jie on 2018/1/6.
 * 实现TextWatcher 这样重写TextWatcher不用重写3个方法了
 */

public class SampleTextWatch implements TextWatcher {
    /**
     * EditText 刚开始的回调
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * EditText 正在变化的回调
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * EditText 改变后的回调
     */
    @Override
    public void afterTextChanged(Editable editable) {

    }
}
