package com.xie.app.enforce.util.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.xie.app.enforce.R;
import com.xie.app.enforce.view.adapter.SelectAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jie on 2018/1/24.
 * SelectDialog
 */

public class SelectDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {

    private DisplayMetrics dm; // 分辨率
    private Context context;
    @BindView(R.id.view_layout)
    FrameLayout mLayout; // FrameLayout
    @BindView(R.id.view_top)
    View viewTop; // 阴影部分
    @BindView(R.id.list)
    ListView listView; // ListView 的控件
    private SelectAdapter adapter;
    private List<String> beans; // 数据
    private ISelectInterface mInterface; // 点击每个Item的回调

    public void setInterface(ISelectInterface mInterface) {
        this.mInterface = mInterface;
    }

    public SelectDialog(@NonNull Context context, List<String> beans) {
        super(context, R.style.custom_dialog);
        this.context = context;
        this.beans = beans;
        dm = context.getResources().getDisplayMetrics();
    }

    public void setAdapterItem(int select) {
        if (adapter != null) adapter.setCheckItem(select);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        // 设置Window样式
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = dm.widthPixels;
        params.height = dm.heightPixels;
        window.setAttributes(params);
        adapter = new SelectAdapter(context, beans);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        viewTop.setOnClickListener(this);
        mLayout.setOnClickListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mInterface != null) {
            mInterface.onDismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        adapter.setCheckItem(position);
        if (mInterface != null) {
            mInterface.onSelect(position);
        }
        dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_top: // 点击最上边隐藏弹窗
                dismiss();
                break;
            case R.id.view_layout: // 点击阴影部分隐藏弹窗
                dismiss();
                break;
        }
    }

    public interface ISelectInterface {
        void onSelect(int position);

        void onDismiss();
    }
}
