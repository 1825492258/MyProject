package com.xie.app.enforce.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xie.app.enforce.R;
import com.xie.app.enforce.view.application.MyApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jie on 2018/1/24.
 * SelectAdapter 适配器
 */

public class SelectAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public SelectAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_layout, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mText.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                holder.mImage.setVisibility(View.VISIBLE);
                holder.mText.setTextColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.text_3385ff));
            } else {
                holder.mImage.setVisibility(View.INVISIBLE);
                holder.mText.setTextColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.text_383a4c));
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_view)
        TextView mText;
        @BindView(R.id.item_image)
        ImageView mImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
