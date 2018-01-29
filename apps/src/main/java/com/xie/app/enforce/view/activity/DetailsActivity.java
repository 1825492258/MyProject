package com.xie.app.enforce.view.activity;

import android.view.View;

import com.xie.app.enforce.R;
import com.xie.app.enforce.util.common.StatusBarUtil;
import com.xie.app.enforce.util.view.ToolBarHelp;
import com.xie.app.enforce.view.activity.base.BaseActivity;

/**
 * 详情界面
 */
public class DetailsActivity extends BaseActivity {


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        super.initView();
        ToolBarHelp mBarHelp = new ToolBarHelp(this);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this,mBarHelp.getToolbar());
        mBarHelp.setTitle(getString(R.string.details));
        mBarHelp.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
