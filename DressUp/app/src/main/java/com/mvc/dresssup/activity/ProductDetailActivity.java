package com.mvc.dresssup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewTreeObserver;

import com.mvc.dresssup.R;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.fragment.ProductDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductDetailActivity extends FragmentActivity {


    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ProductDetailFragment fragment;
    private ProductDetailAdapter productDetailAdapter;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");

        bundle = new Bundle();
        bundle.putString("productId", productId);
        initData();
    }

    private void initData() {
        productDetailAdapter = new ProductDetailAdapter(getSupportFragmentManager());
        viewPager.setAdapter(productDetailAdapter);
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class ProductDetailAdapter extends FragmentStatePagerAdapter {

        public ProductDetailAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new ProductDetailFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
