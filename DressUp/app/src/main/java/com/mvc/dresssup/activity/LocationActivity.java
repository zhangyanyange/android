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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvc.dresssup.R;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.fragment.LocationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationActivity extends FragmentActivity {


    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.rl_fukuan)
    RelativeLayout rlFukuan;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fenge)
    View fenge;
    @BindView(R.id.addLocation)
    TextView addLocation;
    private LocationFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongdi);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, AddNewLocationActivity.class);
                startActivity(intent);
            }
        });

        initData();
    }

    private void initData() {
        viewPager.setAdapter(new LocationAdapter(getSupportFragmentManager()));
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class LocationAdapter extends FragmentStatePagerAdapter {

        public LocationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new LocationFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

}
