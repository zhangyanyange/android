package com.myygjc.ant.project.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.fragment.FindOrderFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FindOrderActivity extends FragmentActivity {

    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.vp_findorder)
    ViewPager vpFindorder;

    private FindOrderFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_order);
        ButterKnife.bind(this);

        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();

    }
    private void initData() {
        vpFindorder.setAdapter(new AddOrderAdapter(getSupportFragmentManager()));
        vpFindorder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                vpFindorder.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    public class AddOrderAdapter extends FragmentStatePagerAdapter {


        public AddOrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new FindOrderFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
