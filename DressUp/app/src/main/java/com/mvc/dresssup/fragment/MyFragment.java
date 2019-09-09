package com.mvc.dresssup.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mvc.dresssup.R;
import com.mvc.dresssup.activity.LocationActivity;
import com.mvc.dresssup.activity.LoginActivity;
import com.mvc.dresssup.activity.OrderManagerActivity;
import com.mvc.dresssup.activity.SettingActivity;
import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/9/6.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.mylocation)
    RelativeLayout mylocation;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.ib_setting)
    ImageButton ibSetting;

    @Override
    public LoadingPager.LoadedResult initData() {
        return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_my, null);
        ButterKnife.bind(this, view);

        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toStartActivity(LoginActivity.class);
            }
        });

        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toStartActivity(LocationActivity.class);
            }
        });

        rlOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIUtils.getContext(), OrderManagerActivity.class);
                startActivity(intent);
            }
        });

        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              toStartActivity(SettingActivity.class);
            }
        });
        return view;
    }

    //跳转activity
    private void toStartActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(UIUtils.getContext(), activity);
        startActivity(intent);
    }



}
