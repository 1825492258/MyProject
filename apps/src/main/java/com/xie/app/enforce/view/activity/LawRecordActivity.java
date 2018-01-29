package com.xie.app.enforce.view.activity;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.xie.app.enforce.R;
import com.xie.app.enforce.model.bean.DataInfo;
import com.xie.app.enforce.util.common.StatusBarUtil;
import com.xie.app.enforce.util.help.BarChartManager;
import com.xie.app.enforce.util.view.SelectDialog;
import com.xie.app.enforce.util.view.TimeDataDialog;
import com.xie.app.enforce.util.view.ToolBarHelp;
import com.xie.app.enforce.view.activity.base.BaseActivity;
import com.xie.app.enforce.view.adapter.LawRecordAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 执法记录
 */
public class LawRecordActivity extends BaseActivity implements View.OnClickListener, AbsListView.OnScrollListener {
    @BindView(R.id.rl_left)
    RelativeLayout mLeftLayout; // 点击左侧出现的下拉框
    @BindView(R.id.rl_right)
    RelativeLayout mRightLayout; // 点击右侧出现的下拉框
    @BindView(R.id.tv_top_left)
    TextView mLeftText; // 左侧的文字
    @BindView(R.id.img_top_left)
    ImageView mLeftImage; // 左侧下面的小图标
    @BindView(R.id.tv_top_right)
    TextView mRightText; // 右侧的文字
    @BindView(R.id.img_top_right)
    ImageView mRightImage; // 右侧下面的小图标
    @BindView(R.id.listView)
    ListView mListView; // ListView 的布局
    @BindView(R.id.item_layout)
    LinearLayout mLayout;
    private String left[] = {"全部违停", "逆向行驶", "占用机动车道", "违章停车", "其他"};
    private String right[] = {"最近7天", "近一个月", "近三个月", "自定义时间段"};
    private View headerView; // 为ListView添加一个头布局
    private BarChart mBarChart; // 柱状图控件
    private List<DataInfo> mData = new ArrayList<>();

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_law_record;
    }

    @Override
    protected void initView() {
        super.initView();
        ToolBarHelp mBarHelp = new ToolBarHelp(this);
        mBarHelp.setTitle(getString(R.string.law_record));
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mBarHelp.getToolbar());
        mBarHelp.setLeftListener(this);
        headerView = View.inflate(this, R.layout.item_heads, null);
        mBarChart = headerView.findViewById(R.id.barChart);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLeftLayout.setOnClickListener(this);
        mRightLayout.setOnClickListener(this);
        mListView.setOnScrollListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        // 模拟添加的数据供柱状图使用
        mData.add(new DataInfo("摩拜", 210));
        mData.add(new DataInfo("ofo", 260));
        mData.add(new DataInfo("酷奇", 146));
        mData.add(new DataInfo("小蓝", 36));
        mData.add(new DataInfo("其他", 123));
        BarChartManager.initBarChart(mBarChart);
        BarChartManager.initBarChart(this, mBarChart, mData);
        mListView.addHeaderView(headerView);
        mListView.addHeaderView(View.inflate(this, R.layout.item_head, null));
        mListView.setAdapter(new LawRecordAdapter(this));
        mLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_left_layout: // 返回按钮
                finish();
                break;
            case R.id.rl_left: // 左侧选择违章类型
                showLeftDialog();
                break;
            case R.id.rl_right: // 右侧选择时间段
                showRightDialog();
                break;
        }
    }

    private SelectDialog mLeftDialog;

    /**
     * 左侧违章原因的选择
     */
    private void showLeftDialog() {
        if (mLeftDialog == null) {
            mLeftDialog = new SelectDialog(this, Arrays.asList(left));
            mLeftDialog.setInterface(new SelectDialog.ISelectInterface() {
                @Override
                public void onSelect(int position) {
                    mLeftText.setText(left[position]);
                }

                @Override
                public void onDismiss() {
                    mLeftImage.setImageResource(R.drawable.ic_data_bottom);
                }
            });
        }
        mLeftDialog.show();
        mLeftImage.setImageResource(R.drawable.ic_data_top);
    }

    private SelectDialog mRightDialog;
    private int rightSelect = 0;

    /**
     * 右侧时间选择的弹窗
     */
    private void showRightDialog() {
        if (mRightDialog == null) {
            mRightDialog = new SelectDialog(this, Arrays.asList(right));
        }
        mRightDialog.setAdapterItem(rightSelect);
        mRightDialog.setInterface(new SelectDialog.ISelectInterface() {
            @Override
            public void onSelect(int position) {
                switch (position) {
                    case 0: // 最近7天
                        rightSelect = position;
                        mRightText.setText(right[position]);
                        break;
                    case 1: // 近一个月
                        rightSelect = position;
                        mRightText.setText(right[position]);
                        break;
                    case 2: // 近三个月
                        rightSelect = position;
                        mRightText.setText(right[position]);
                        break;
                    case 3: // 自定义时间段
                        showTimeDialog();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onDismiss() {
                mRightImage.setImageResource(R.drawable.ic_data_bottom);
            }
        });
        mRightDialog.show();
        mRightImage.setImageResource(R.drawable.ic_data_top);
    }

    /**
     * 展示自定义时间选择的弹窗
     */
    private TimeDataDialog mTimeDialog;

    private void showTimeDialog() {
        if (mTimeDialog == null) {
            mTimeDialog = new TimeDataDialog(this);
            mTimeDialog.setDateTime(new TimeDataDialog.IDateTime() {
                @Override
                public void onEnsure(String start, String end) {
                    mRightText.setText("自选时间段");
                    rightSelect = 3;
                }
            });
        }
        mTimeDialog.show();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int i2) {
        if(firstVisibleItem >= 1){
            mLayout.setVisibility(View.VISIBLE);
        }else {
            mLayout.setVisibility(View.GONE);
        }
    }
}
