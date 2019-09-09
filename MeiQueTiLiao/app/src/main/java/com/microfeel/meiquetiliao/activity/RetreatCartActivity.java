package com.microfeel.meiquetiliao.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.base.LoadingPager;
import com.microfeel.meiquetiliao.fragment.RetreatCartFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RetreatCartActivity extends FragmentActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private RetreatCartFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreat_cart);
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
        viewPager.setAdapter(new ChooiceLocationAdapter(getSupportFragmentManager()));
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class ChooiceLocationAdapter extends FragmentStatePagerAdapter {

        public ChooiceLocationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new RetreatCartFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
