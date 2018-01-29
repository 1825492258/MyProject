package com.xie.app.enforce.util.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xie.app.enforce.R;
import com.xie.app.enforce.view.application.MyApplication;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jie on 2018/1/25.
 * 选择违规类型的弹窗
 */

public class SelectTypeDialog extends Dialog {
    @BindView(R.id.listView)
    ListView mListView; // ListView控件
    @BindView(R.id.dialog_cancel)
    ImageView mCancelImage; // X 的图标
    private Context context;
    private String list[] = {"逆向行驶", "占用机动车道", "占用人行道", "违章停车", "其他"}; // 数据
    private SelectType adapter; // 适配器
    private SelectDialog.ISelectInterface mInterface; // 接口回调

    public void setInterface(SelectDialog.ISelectInterface mInterface) {
        this.mInterface = mInterface;
    }

    public SelectTypeDialog(@NonNull Context context) {
        super(context, R.style.SheetDialogStyle);
        this.context = context;
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_type_layout);
        ButterKnife.bind(this);
        adapter = new SelectType(context, Arrays.asList(list));
        mListView.setAdapter(adapter);
        mCancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setCheckItem(i);
                if (mInterface != null) {
                    mInterface.onSelect(i);
                }
                dismiss();
            }
        });
    }

    /**
     * 定义的适配器
     */
    private class SelectType extends BaseAdapter {
        private Context context; // 上下文对象
        private List<String> list; // 数据
        private int checkItemPosition = 0; // 第几个Item被选中

        private void setCheckItem(int position) {
            this.checkItemPosition = position;
            notifyDataSetChanged();
        }

        private SelectType(Context context, List<String> list) {
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_type_adapter, viewGroup, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.mText.setText(list.get(i));
            // 设置选中的Item的字体颜色的变化
            if (i == checkItemPosition) {
                holder.mText.setTextColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.text_3385ff));
            } else {
                holder.mText.setTextColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.text_212121));
            }
            return view;
        }
    }

    static class ViewHolder {
        @BindView(R.id.item_text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
