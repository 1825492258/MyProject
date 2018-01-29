package com.xie.app.enforce.util.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xie.app.enforce.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jie on 2018/1/4.
 * 提示弹窗
 */

public class HintDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.item_title)
    TextView mTitle; // 提示的标题控件
    @BindView(R.id.item_msg)
    TextView mMessage; // 提示的内容控件
    @BindView(R.id.item_cancel)
    TextView mCancel; // 取消按钮的控件
    @BindView(R.id.item_sure)
    TextView mEnsure; // 确定按钮的控件

    private String title; // 弹窗标题
    private String message; // 标题信息
    private IDialogEnsure mDialogEnsure; // 弹窗的回调

    public HintDialog(@NonNull Context context) {
        super(context, R.style.SheetDialogStyle);
        setCanceledOnTouchOutside(false); // 设置外部点击屏幕弹窗不消失
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hint);
        ButterKnife.bind(this);
        mTitle.setText(getTitle());
        mMessage.setText(getMessage());
        mCancel.setOnClickListener(this);
        mEnsure.setOnClickListener(this);
    }

    private String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDialogEnsure(IDialogEnsure mDialogEnsure) {
        this.mDialogEnsure = mDialogEnsure;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_cancel:
                if (mDialogEnsure != null) {
                    mDialogEnsure.onCancel();
                    dismiss();
                }
                break;
            case R.id.item_sure:
                if (mDialogEnsure != null) {
                    mDialogEnsure.onEnsure();
                    dismiss();
                }
                break;
        }
    }

    /**
     * 定义一个接口，点击确定时进行回调
     */
    public interface IDialogEnsure {

        void onEnsure();

        void onCancel();
    }
}