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
import com.myygjc.ant.project.fragment.WorkHomeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LookShopActivity extends FragmentActivity {

    @Bind(R.id.vp_shopcar)
    ViewPager vpShopcar;
    private WorkHomeFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loo_kshop);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        vpShopcar.setAdapter(new LookCarAdapter(getSupportFragmentManager()));
        vpShopcar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                vpShopcar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class LookCarAdapter extends FragmentStatePagerAdapter {

        public LookCarAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
             fragment = new WorkHomeFragment();
            return fragment;
        }


        @Override
        public int getCount() {
            return 1;
        }

    }
}
