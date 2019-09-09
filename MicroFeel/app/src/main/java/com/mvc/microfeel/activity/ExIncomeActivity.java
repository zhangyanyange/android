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

import com.mvc.microfeel.R;
import com.mvc.microfeel.base.LoadingPager;
import com.mvc.microfeel.fragment.ExIncomeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExIncomeActivity extends FragmentActivity {

    @Bind(R.id.back)
    ImageButton back;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private ExIncomeFragment fragment;
    private String projectID;
    private Bundle bundle;
    private int accountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_income);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        projectID = intent.getStringExtra("projectID");
        accountID = intent.getIntExtra("accountID", 0);
        bundle = new Bundle();
        bundle.putString("projectID",projectID);
        bundle.putInt("accountID",accountID);
        initData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        viewPager.setAdapter(new LookerOrderAdapter(getSupportFragmentManager()));
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class LookerOrderAdapter extends FragmentStatePagerAdapter {

        public LookerOrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new ExIncomeFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

    }
}
