package com.xie.app.enforce.view.activity;

import android.view.View;

import com.xie.app.enforce.R;
import com.xie.app.enforce.util.common.StatusBarUtil;
import com.xie.app.enforce.util.view.ToolBarHelp;
import com.xie.app.enforce.view.activity.base.BaseActivity;

/**
 * 关于携尔
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        super.initView();
        ToolBarHelp mBarHelp = new ToolBarHelp(this);
        mBarHelp.setTitle(getString(R.string.about_us));
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mBarHelp.getToolbar());
        mBarHelp.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
