package com.myygjc.ant.project.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewTreeObserver;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.doman.GetCarBean;
import com.myygjc.ant.project.doman.ResultMessage;
import com.myygjc.ant.project.fragment.AddOrderFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserDetailActivity extends FragmentActivity {
    @Bind(R.id.vp_order)
    ViewPager vpOrder;

    private Bundle bundle;
    private AddOrderFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_userdetail);
        ButterKnife.bind(this);
        ArrayList<Integer> choicePostion = getIntent().getIntegerArrayListExtra("choice");
        ArrayList<GetCarBean> data = getIntent().getParcelableArrayListExtra("data");
        bundle = new Bundle();
        bundle.putIntegerArrayList("choice", choicePostion);
        bundle.putParcelableArrayList("data",data);
        initData();
    }

    private void initData() {
        vpOrder.setAdapter(new AddOrderAdapter(getSupportFragmentManager()));
        vpOrder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                vpOrder.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    public class AddOrderAdapter extends FragmentStatePagerAdapter {

        public AddOrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new AddOrderFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(ResultMessage messageEvent) {
        finish();
    }
}
