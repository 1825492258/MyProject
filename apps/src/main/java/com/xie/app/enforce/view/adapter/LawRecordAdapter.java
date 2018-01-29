package com.xie.app.enforce.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xie.app.enforce.R;
import com.xie.app.enforce.view.activity.DetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jie on 2018/1/24.
 * 罚款记录的适配器
 */

public class LawRecordAdapter extends BaseAdapter {
    private Context context;

    public LawRecordAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_law_layout, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, DetailsActivity.class));
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_name)
        TextView mNameText; // 违章人

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
