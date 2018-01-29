package com.xie.app.enforce.util.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;


import com.xie.app.enforce.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jie on 2018/1/12.
 * DatePicker 时间选择的弹窗
 */

public class TimeDataDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.end_date)
    DatePicker endDatePicker; // 结束时间
    @BindView(R.id.start_date)
    DatePicker startDatePicker; // 开始时间
    @BindView(R.id.date_sure)
    TextView mSureView; // 确定
    @BindView(R.id.date_cacle)
    TextView mCancelView; // 取消
    private IDateTime mDateTime;
    @BindView(R.id.hint_text)
    TextView mHintText; // 提示词

    public void setDateTime(IDateTime mDateTime) {
        this.mDateTime = mDateTime;
    }

    public TimeDataDialog(@NonNull Context context) {
        super(context, R.style.MyTimeStyle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time_data);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        // 设置开始时间 当前时间的上个月
        Calendar calendar = Calendar.getInstance();
        startDatePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) - 1,
                calendar.get(Calendar.DAY_OF_MONTH), null);
        mSureView.setOnClickListener(this);
        mCancelView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_sure: // 点击确定按钮
                String start = startDatePicker.getYear() + "-" + (startDatePicker.getMonth() + 1) + "-" + startDatePicker.getDayOfMonth() + " 23:00:00";
                String end = endDatePicker.getYear() + "-" + (endDatePicker.getMonth() + 1) + "-" + endDatePicker.getDayOfMonth() + " 23:00:00";
                if (start.compareTo(end) >= 0) { // 开始时间大于了结束时间
                    mHintText.setVisibility(View.VISIBLE);
                    return;
                }
                if (mDateTime != null) {
                    mDateTime.onEnsure(start, end);
                }
                dismiss();
                break;
            case R.id.date_cacle: // 点击取消按钮
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mHintText.setVisibility(View.INVISIBLE);
    }

    /**
     * 点击确定时的返回
     */
    public interface IDateTime {
        /**
         * @param start 开始时间
         * @param end   结束时间
         */
        void onEnsure(String start, String end);
    }
}
