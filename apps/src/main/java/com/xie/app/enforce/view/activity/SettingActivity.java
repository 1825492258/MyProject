package com.xie.app.enforce.view.activity;

import android.view.View;

import com.xie.app.enforce.R;
import com.xie.app.enforce.util.common.StatusBarUtil;
import com.xie.app.enforce.util.view.HintDialog;
import com.xie.app.enforce.util.view.ToolBarHelp;
import com.xie.app.enforce.view.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        // 状态栏的设置
        ToolBarHelp mBarHelp = new ToolBarHelp(this);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mBarHelp.getToolbar());
        mBarHelp.setTitle(getString(R.string.left_two));
        mBarHelp.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.set_update, R.id.set_clear, R.id.set_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_update: // 检查更新
                break;
            case R.id.set_clear: // 清理缓存
                break;
            case R.id.set_exit: // 退出登录
                setExitDialog();
                break;
        }
    }

    private HintDialog mDialog;

    /**
     * 退出登录
     */
    private void setExitDialog() {
        if (mDialog == null) {
            mDialog = new HintDialog(this);
            mDialog.setTitle(getString(R.string.hint));
            mDialog.setMessage(getString(R.string.exit_message));

        }
        mDialog.setDialogEnsure(new HintDialog.IDialogEnsure() {
            @Override
            public void onEnsure() {
                SettingActivity.this.killAll();
            }

            @Override
            public void onCancel() {

            }
        });
        mDialog.show();
    }
}
