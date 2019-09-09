package com.mvc.dresssup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

import com.mvc.dresssup.R;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.fragment.OrderDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends FragmentActivity {
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private OrderDetailFragment fragment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);
        bundle = new Bundle();
        bundle.putInt("Id",id);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
    }

    private void initData() {
        viewPager.setAdapter(new OrderDetailAdapter(getSupportFragmentManager()));
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class OrderDetailAdapter extends FragmentStatePagerAdapter {

        public OrderDetailAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new OrderDetailFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
