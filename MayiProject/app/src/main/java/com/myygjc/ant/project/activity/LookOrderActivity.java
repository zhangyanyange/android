package com.myygjc.ant.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.fragment.LookerOrderFragment;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LookOrderActivity extends FragmentActivity {

    @Bind(R.id.iv_arrow)
    ImageView ivArrow;

    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.vp_lookorder)
    ViewPager vpLookorder;
    @Bind(R.id.tv_yubfei)
    TextView tvYubfei;
    private LookerOrderFragment fragment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_order);
        ButterKnife.bind(this);

        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String shdz = intent.getStringExtra("shdz");
        double zyf = intent.getDoubleExtra("zyf", 0);
        tvLocation.setText(shdz);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        tvYubfei.setText(format.format(zyf));
        bundle = new Bundle();
        bundle.putString("id", id);
        initData();
    }

    private void initData() {
        vpLookorder.setAdapter(new LookerOrderAdapter(getSupportFragmentManager()));
        vpLookorder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                vpLookorder.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class LookerOrderAdapter extends FragmentStatePagerAdapter {

        public LookerOrderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new LookerOrderFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

    }
}
