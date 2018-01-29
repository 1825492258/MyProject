package com.xie.app.enforce.view.activity;

import android.view.View;
import android.widget.RelativeLayout;
import com.xie.app.enforce.R;
import com.xie.app.enforce.util.common.StatusBarUtil;
import com.xie.app.enforce.util.view.SelectDialog;
import com.xie.app.enforce.util.view.SelectTypeDialog;
import com.xie.app.enforce.util.view.ToolBarHelp;
import com.xie.app.enforce.view.activity.base.BaseActivity;

import butterknife.BindView;

/**
 * 罚款界面
 */
public class PenaltyActivity extends BaseActivity implements SelectDialog.ISelectInterface {

    @BindView(R.id.rl_penalty_select)
    RelativeLayout mSelectType; // 选择违规类型

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_penalty;
    }

    @Override
    protected void initView() {
        super.initView();
        ToolBarHelp mBarHelp = new ToolBarHelp(this);
        mBarHelp.setTitle(getString(R.string.penalty));
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mBarHelp.getToolbar());
        mBarHelp.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSelectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog();
            }
        });
    }

    /**
     * 展示选择违规类型的弹窗
     */
    private SelectTypeDialog mDialog;

    private void showTypeDialog() {
        if (mDialog == null) {
            mDialog = new SelectTypeDialog(this);
            mDialog.setInterface(this);
        }
        mDialog.show();
    }

    /**
     * 选择罚款的类型
     * @param position 第几个
     */
    @Override
    public void onSelect(int position) {

    }

    @Override
    public void onDismiss() {

    }
}
