package com.xie.app.enforce.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xie.app.enforce.R;
import com.xie.app.enforce.view.activity.AboutActivity;
import com.xie.app.enforce.view.activity.LawRecordActivity;
import com.xie.app.enforce.view.activity.PenaltyActivity;
import com.xie.app.enforce.view.activity.SettingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 侧滑界面
 */
public class LeftFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.left_record, R.id.left_customer, R.id.left_setting, R.id.left_about, R.id.left_view})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.left_record: // 罚单记录
                startActivity(new Intent(getActivity(), LawRecordActivity.class));
                break;
            case R.id.left_setting: // 系统设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.left_customer: // 联系客服
                startActivity(new Intent(getActivity(), PenaltyActivity.class)); // 这里先调到罚款的界面
                break;
            case R.id.left_about: // 关于我们
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            default:
                break;
        }
    }
}
