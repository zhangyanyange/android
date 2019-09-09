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
import android.widget.TextView;

import com.mvc.dresssup.R;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.fragment.PopularbrandsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularbrandsActivity extends FragmentActivity {


    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.brand_name)
    TextView brandName;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private PopularbrandsFragment fragment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popularbrands);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String brand = intent.getStringExtra("brand");
        String brandName1 = intent.getStringExtra("brandName");
        bundle = new Bundle();
        bundle.putString("brand", brand);
        brandName.setText(brandName1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
    }

    private void initData() {
        viewPager.setAdapter(new PopularbrandsAdapter(getSupportFragmentManager()));
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class PopularbrandsAdapter extends FragmentStatePagerAdapter {

        public PopularbrandsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new PopularbrandsFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
