package com.xie.app.enforce.view.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xie.app.enforce.R;
import com.xie.app.enforce.util.common.StatusBarUtil;
import com.xie.app.enforce.util.view.ToolBarHelp;
import com.xie.app.enforce.view.activity.base.BaseActivity;

import butterknife.BindView;

/**
 * 扫一扫的界面
 */
public class ScanCodeActivity extends BaseActivity implements View.OnClickListener {

    private boolean isOpen = false; // 是否打开灯
    @BindView(R.id.tv_scan_flash)
    TextView tvScanFlash; // 打开手电筒的控件

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    protected void initView() {
        super.initView();
        // 状态栏的设置
        ToolBarHelp mToolBarHelp = new ToolBarHelp(this);
        mToolBarHelp.setTitle("扫一扫");
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolBarHelp.getToolbar());
        mToolBarHelp.setLeftListener(this);
        tvScanFlash.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        // 为二维码扫描界面设置制定化界面
        CaptureFragment captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_my_container, captureFragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_left_layout:
                finish();
                break;
            case R.id.tv_scan_flash: // 打开手电筒
                CodeUtils.isLightEnable(!isOpen);
                tvScanFlash.setText(isOpen ? getString(R.string.open_flash) : getString(R.string.close_flash));
                tvScanFlash.setTextColor(isOpen ? getResources().getColor(R.color.white) : getResources().getColor(R.color.text_3385ff));
                Drawable top = getResources().getDrawable(isOpen ? R.drawable.ic_flash_open : R.drawable.ic_flash_close);
                top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
                tvScanFlash.setCompoundDrawables(null, top, null, null);
                isOpen = !isOpen;
                break;
        }
    }
    // 返回的回调
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            //Toast.makeText(SecondActivity.this,result,Toast.LENGTH_SHORT).show();
            resultIntent.putExtras(bundle);
            ScanCodeActivity.this.setResult(RESULT_OK, resultIntent);
            ScanCodeActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            ScanCodeActivity.this.setResult(RESULT_OK, resultIntent);
            ScanCodeActivity.this.finish();
        }
    };
}
