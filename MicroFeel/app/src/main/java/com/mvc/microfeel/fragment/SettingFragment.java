package com.mvc.microfeel.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.mvc.microfeel.R;
import com.mvc.microfeel.activity.ChatListActivity;
import com.mvc.microfeel.activity.HousesActivity;
import com.mvc.microfeel.activity.PersonScoreActivity;
import com.mvc.microfeel.activity.UniversallyActivity;
import com.mvc.microfeel.util.PrefUtils;
import com.mvc.microfeel.util.UIUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/8/10.
 */
public class SettingFragment extends TakePhotoFragment {
    @Bind(R.id.info)
    RelativeLayout info;
    @Bind(R.id.rl_score)
    RelativeLayout rlScore;
    @Bind(R.id.header)
    ImageView header;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_gongsi)
    TextView tvGongsi;
    @Bind(R.id.ib_setting)
    ImageButton ibSetting;
    @Bind(R.id.money_shenyu)
    TextView moneyShenyu;
    @Bind(R.id.rl_money)
    RelativeLayout rlMoney;
    @Bind(R.id.rl_talk)
    RelativeLayout rlTalk;
    private TakePhoto takePhoto;


//    @Override
//    public LoadingPager.LoadedResult initData() {
//        return LoadingPager.LoadedResult.SUCCESS;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_setting, null);
        ButterKnife.bind(this, view);

        takePhoto = getTakePhoto();

        /**
         * 个人信息
         */
        tvName.setText(PrefUtils.getString(UIUtils.getContext(), "tv_name", ""));
        tvPhone.setText(PrefUtils.getString(UIUtils.getContext(), "tv_phone", ""));
        tvGongsi.setText(PrefUtils.getString(UIUtils.getContext(), "tv_gongsi", ""));
        //跳转到设置界面
        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UIUtils.getContext(), UniversallyActivity.class));
            }
        });

        //进入聊天室
        rlTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UIUtils.getContext(), ChatListActivity.class));
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIUtils.getContext(), HousesActivity.class);
                startActivity(intent);
            }
        });

        rlScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIUtils.getContext(), PersonScoreActivity.class);
                startActivity(intent);
            }
        });

        //更换头像
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView("上传头像", null, "取消", null,
                        new String[]{"拍照", "从相册中选择"},
                        getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                        Uri imageUri = Uri.fromFile(file);
                        if (position == 1) {

                            takePhoto.onEnableCompress(null, false);

                            TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();

                            builder.setWithOwnGallery(true);

                            takePhoto.setTakePhotoOptions(builder.create());

                            takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());

                        } else if (position == 0) {
                            takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                        }
                    }
                }).show();
            }
        });
        return view;
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(800).setOutputY(800);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Glide.with(this).load(new File(result.getImages().get(0).getOriginalPath())).into(header);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
