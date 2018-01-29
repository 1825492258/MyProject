package com.xie.app.enforce.util.help;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.xie.app.enforce.R;
import com.xie.app.enforce.model.bean.DataInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jie on 2018/1/24.
 * BarChart 的基本的配置
 */

public class BarChartManager {

    public static void initBarChart(BarChart mBarChart) {
        // 背景阴影
        mBarChart.setDrawBarShadow(false);
        // 设置图表是否支持触控操作
        mBarChart.setTouchEnabled(false);
        // 设置整体背景颜色
        mBarChart.setBackgroundColor(Color.WHITE);
        // 设置网格格式
        mBarChart.setDrawGridBackground(false);
        mBarChart.setHighlightFullBarEnabled(false);
        // 不设置描述
        mBarChart.getDescription().setEnabled(false);
        // Legend 不显示
        mBarChart.getLegend().setEnabled(false);
        // 右侧的Y轴不显示
        mBarChart.getAxisRight().setEnabled(false);
        // 退左侧Y轴的设置
        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextSize(10f);
        leftAxis.setSpaceTop(15f);
        // X轴的设置
        XAxis xAxis = mBarChart.getXAxis();
        //xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X在下方
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(5);
        xAxis.setTextSize(10f);
        // 设置动画的效果
        mBarChart.animateY(1000, Easing.EasingOption.Linear); // Y轴向上生长的动画
    }

    public static void initBarChart(Context context, BarChart mBarChart, final List<DataInfo> mData) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            yValues.add(new BarEntry(i, mData.get(i).getNumber()));
        }
        BarDataSet set;
        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set.setValues(yValues);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yValues, "");
            //set.setColor(Color.BLUE);
            set.setColor(context.getResources().getColor(R.color.color_418dff));
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTextColor(context.getResources().getColor(R.color.color_418dff));
            data.setBarWidth(0.4f);
            mBarChart.setData(data);
        }

        //mBarChart.getXAxis().setAxisMinimum(0f);
        //mBarChart.getXAxis()
        mBarChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int)value;
                return index >=0 && index < mData.size() ? mData.get(index).getType() : "";
            }
        });
        mBarChart.invalidate();
    }
}
