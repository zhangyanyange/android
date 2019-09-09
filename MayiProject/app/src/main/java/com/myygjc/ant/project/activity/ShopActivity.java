package com.myygjc.ant.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gxz.PagerSlidingTabStrip;
import com.myygjc.ant.project.R;
import com.myygjc.ant.project.adapter.ItemTitlePagerAdapter;
import com.myygjc.ant.project.fragment.DetailsFragment;
import com.myygjc.ant.project.fragment.ShopFragment;
import com.myygjc.ant.project.util.ToastUtils;
import com.myygjc.ant.project.view.NoScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/1/14.
 */

public class ShopActivity extends FragmentActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.psts_tabs)
    PagerSlidingTabStrip pstsTabs;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.vp_content)
    NoScrollView vpContent;
    @Bind(R.id.lookshopcar)
    ImageButton lookshopcar;

    private int id;
    private int total;
    private String aa1;
    private String name;
    private double price;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getDoubleExtra("price", 0);
        String tp = intent.getStringExtra("TP");
        String mstp = intent.getStringExtra("mstp");
        total = (int) intent.getDoubleExtra("total", 0);
        id = intent.getIntExtra("id", 0);
        ButterKnife.bind(this);

        ShopFragment shopFragment = new ShopFragment();
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        Bundle bundle1 = new Bundle();

        bundle.putString("name", name);
        bundle.putDouble("price", price);
        bundle.putInt("id", id);
        bundle.putString("tp", tp);
        bundle.putInt("total", total);
        bundle1.putString("mstp", mstp);
        shopFragment.setArguments(bundle);
        detailsFragment.setArguments(bundle1);
        fragmentList.add(shopFragment);

        fragmentList.add(detailsFragment);

        vpContent.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"商品", "详情"}));
        vpContent.setOffscreenPageLimit(2);
        pstsTabs.setViewPager(vpContent);
        lookshopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ShopActivity.this,LookShopActivity.class);
                startActivity(intent1);
            }
        });
        initListener();
    }

    public void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (ToastUtils.getCenterToast() != null) {
                    ToastUtils.cancle();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
        if (ToastUtils.getCenterToast() != null) {
            ToastUtils.cancle();
        }
    }
}
