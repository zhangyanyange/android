package com.mvc.dresssup.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mvc.dresssup.R;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.domain.TabEntity;
import com.mvc.dresssup.fragment.OrderAllFragment;
import com.mvc.dresssup.fragment.OrderCancelFragment;
import com.mvc.dresssup.fragment.OrderNoMoneyFragment;
import com.mvc.dresssup.fragment.OrderShouHuoFragment;
import com.mvc.dresssup.fragment.OrderWanChengFragment;
import com.mvc.dresssup.view.NoScroolViewPager;
import com.mvc.dresssup.view.PreViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderManagerActivity extends FragmentActivity {


    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.rl_fukuan)
    RelativeLayout rlFukuan;
    @BindView(R.id.tl_4)
    CommonTabLayout tl4;
    @BindView(R.id.view_pager)
    NoScroolViewPager mViewPager;
    @BindView(R.id.activity_order_manager)
    LinearLayout activityOrderManager;
    private OrderAllFragment fragment;

    private String[] mTitles = {"全部", "待付款", "待收货", "已完成", "已取消"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private MyPageChangeListener listener;
    private OrderNoMoneyFragment noMoneyFragment;
    private OrderShouHuoFragment shouHuoFragment;
    private OrderWanChengFragment wanChengFragment;
    private OrderCancelFragment orderCancelFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        ButterKnife.bind(this);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        tl4.setTabData(mTabEntities);
        tl4.setCurrentTab(0);
        tl4.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        OrderManagerAdapter adapter = new OrderManagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        listener = new MyPageChangeListener();
        mViewPager.setOnPageChangeListener(listener);
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onPageSelected(0);
                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }


    class OrderManagerAdapter extends FragmentStatePagerAdapter {

        public OrderManagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                fragment = new OrderAllFragment();
                return fragment;
            } else if (position == 1) {
                noMoneyFragment = new OrderNoMoneyFragment();
                return noMoneyFragment;
            } else if (position == 2) {
                shouHuoFragment = new OrderShouHuoFragment();
                return shouHuoFragment;
            } else if (position == 3) {
                wanChengFragment = new OrderWanChengFragment();
                return wanChengFragment;
            } else if (position == 4) {
                orderCancelFragment = new OrderCancelFragment();
                return orderCancelFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    public class MyPageChangeListener implements PreViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 页面被选中时调用方法,数据应该在此时加载
         *
         * @param position 第几页数据
         */
        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
            } else if (position == 1) {
                LoadingPager loadingPager = noMoneyFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            } else if (position == 2) {
                LoadingPager loadingPager = shouHuoFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            } else if (position == 3) {
                LoadingPager loadingPager = wanChengFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            } else if (position == 4) {
                LoadingPager loadingPager = orderCancelFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}


