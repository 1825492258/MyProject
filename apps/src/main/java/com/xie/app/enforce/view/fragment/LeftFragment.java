package com.xie.app.enforce.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
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

    @OnClick({R.id.img_left_photo, R.id.left_record, R.id.left_customer, R.id.left_setting, R.id.left_about, R.id.left_view})
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
            case R.id.img_left_photo:
                setChangeImage();
                break;
            case R.id.left_view:
                break;
            default:
                break;
        }
    }

    private void setChangeImage() {
        PictureSelector.create(getActivity())
                .openGallery(PictureMimeType.ofImage()) // 获取所有的图片
                .theme(R.style.picture_white_style)
                .maxSelectNum(1) // 设置最大选择数量
                .imageSpanCount(4) // 设置每行显示个数
                .selectionMode(PictureConfig.SINGLE) // 单选
                .isCamera(true) // 是否显示拍照按钮
                .sizeMultiplier(0.5f)
                .enableCrop(true) // 是否剪切
                .compress(true) // 是否压缩
                .hideBottomControls(true) // 是否显示uCrop工具栏
                .circleDimmedLayer(true) // 是否圆形剪切
                .showCropFrame(false) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false) // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isGif(true) // 是否显示GIF图片
                .minimumCompressSize(100) // 小于100kb不进行压缩
                .rotateEnabled(true) // 剪切是否可旋转图片
                .scaleEnabled(true) // 剪切是否可放大缩小图片
                .forResult(PictureConfig.CHOOSE_REQUEST); // 结果回调
    }
}
