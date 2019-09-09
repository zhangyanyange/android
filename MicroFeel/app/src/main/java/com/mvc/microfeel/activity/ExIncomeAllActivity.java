package com.mvc.microfeel.activity;

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
import android.widget.LinearLayout;

import com.mvc.microfeel.R;
import com.mvc.microfeel.base.LoadingPager;
import com.mvc.microfeel.fragment.ExIncomeAllFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ExIncomeAllActivity extends FragmentActivity {

    @Bind(R.id.back)
    ImageButton back;
    @Bind(R.id.activity_ex_income_all)
    LinearLayout activityExIncomeAll;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private ExIncomeAllFragment fragment;
    private String projectID;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_income_all);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        projectID = intent.getStringExtra("projectID");
        bundle = new Bundle();
        bundle.putString("projectID",projectID);
        initData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        viewPager.setAdapter(new ExINAdapter(getSupportFragmentManager()));
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class ExINAdapter extends FragmentStatePagerAdapter {

        public ExINAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new ExIncomeAllFragment();
           fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

    }
}
