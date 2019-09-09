package com.microfeel.meiquetiliao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.base.LoadingPager;
import com.microfeel.meiquetiliao.domain.ChangeTab;
import com.microfeel.meiquetiliao.fragment.MentionMaterialFragment.ModifyFragment;
import com.microfeel.meiquetiliao.fragment.MentionMaterialFragment.ReturnMaterialFragment;
import com.microfeel.meiquetiliao.fragment.MentionMaterialFragment.ShippedFragment;
import com.microfeel.meiquetiliao.fragment.MentionMaterialFragment.SureFragment;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.UIUtils;
import com.microfeel.meiquetiliao.view.NoScroolViewPager;
import com.microfeel.meiquetiliao.view.PreViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.microfeel.meiquetiliao.R.id.tv_name;

public class MentionMaterialActivity extends FragmentActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(tv_name)
    TextView tvName;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tl_1)
    SegmentTabLayout tabLayout_1;
    @Bind(R.id.viewpager)
    NoScroolViewPager viewpager;
    @Bind(R.id.commit_material)
    RelativeLayout commitMaterial;
    @Bind(R.id.retreat_material)
    RelativeLayout retreatMaterial;


    private String[] mTitles = {"可修改", "已确定", "已配送", "退料单"};
    private SureFragment sureFragment;
    private ModifyFragment fragment;
    private ShippedFragment shippedFragment;
    private ReturnMaterialFragment returnMaterialFragment;
    private MentionMaterialListener listener;
    private MentionMaterialAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mention_material);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tvName.setText(PrefUtils.getString(UIUtils.getContext(), "customerName", ""));
        tvLocation.setText(PrefUtils.getString(UIUtils.getContext(), "location", ""));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tabLayout_1.setTabData(mTitles);
        adapter = new MentionMaterialAdapter(getSupportFragmentManager());

        viewpager.setAdapter(adapter);
        listener = new MentionMaterialListener();
        viewpager.setOnPageChangeListener(listener);

        viewpager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onPageSelected(0);
                viewpager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        tabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        commitMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIUtils.getContext(), MaterialActivity.class);
                startActivity(intent);
            }
        });
        //退料
        retreatMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MentionMaterialActivity.this, RetreatCartActivity.class);
                startActivity(intent);
            }
        });
    }

    public class MentionMaterialAdapter extends FragmentStatePagerAdapter {

        public MentionMaterialAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                fragment = new ModifyFragment();
                return fragment;
            } else if (position == 1) {
                sureFragment = new SureFragment();
                return sureFragment;
            } else if (position == 2) {
                shippedFragment = new ShippedFragment();
                return shippedFragment;
            }else if (position == 3) {
                returnMaterialFragment = new ReturnMaterialFragment();
                return returnMaterialFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    private class MentionMaterialListener implements PreViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
            } else if (position == 1) {
                LoadingPager loadingPager = sureFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            } else if (position == 2) {
                LoadingPager loadingPager = shippedFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            }else if(position==3){
                LoadingPager loadingPager = returnMaterialFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payComplete(ChangeTab payComplete) {
        if(payComplete.getPosition()==0){
            viewpager.setCurrentItem(2, false);
            tabLayout_1.setCurrentTab(payComplete.getPosition());
            viewpager.setCurrentItem(payComplete.getPosition(), false);
        }else{
            tabLayout_1.setCurrentTab(payComplete.getPosition());
            viewpager.setCurrentItem(payComplete.getPosition(), false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
