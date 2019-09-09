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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvc.dresssup.R;
import com.mvc.dresssup.adapter.PinPaiAdapter;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.base.MyApplication;
import com.mvc.dresssup.domain.ClassificationProduct;
import com.mvc.dresssup.fragment.ClassificationFragment;
import com.mvc.dresssup.procotol.ClassificationProductProcotol;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassificationActivity extends FragmentActivity {


    public int mPosition = 0;
    @BindView(R.id.loadView)
    ProgressBar loadView;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.rl_fukuan)
    RelativeLayout rlFukuan;
    @BindView(R.id.iv_brand)
    ImageView ivBrand;
    @BindView(R.id.pinpai)
    ListView pinpai;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.activity_classification)
    RelativeLayout activityClassification;


    private Bundle bundle;
    private ClassificationAdapter classificationAdapter;
    private ClassificationFragment fragment;
    private List<ClassificationProduct.CategorysBean> categorys;
    private PinPaiAdapter adapter;
    private ClassificationProduct product;
    private ArrayList<ClassificationProduct.ProductsBean> productsBean;

    private ArrayList<ClassificationProduct.ProductsBean> productsBeanArrayList = new ArrayList<>();
    ArrayList<ClassificationProduct.ProductsBean> list = new ArrayList<ClassificationProduct.ProductsBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final String bigcategory = intent.getStringExtra("bigcategory");
        final String brand = intent.getStringExtra("brand");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        getData(bigcategory, brand);
        bundle = new Bundle();
        pinpai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mPosition = position;
                adapter.notifyDataSetChanged();
                list.clear();

                if (categorys.get(position).getId() == "") {
                    list.addAll(productsBeanArrayList);
                    EventBus.getDefault().post(list);
                    return;
                }

                for (int i = 0; i < productsBeanArrayList.size(); i++) {
                    if (categorys.get(position).getId().equals(productsBeanArrayList.get(i).getCategory())) {
                        list.add(productsBeanArrayList.get(i));
                    }
                }
                EventBus.getDefault().post(list);
            }
        });


    }

    //请求数据
    private void getData(final String bigcategory, final String brand) {
        loadView.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                product = ClassificationProductProcotol.getProduct(bigcategory, "", brand);
                categorys = product.getCategorys();
                productsBean = product.getProducts();
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadView.setVisibility(View.GONE);
                        tvBrand.setText(product.getBrand().getName());
                        adapter = new PinPaiAdapter(ClassificationActivity.this, categorys);
                        pinpai.setAdapter(adapter);
//                        classificationProduct=product
                        productsBean = product.getProducts();

                        for (int i = 0; i < product.getProducts().size(); i++) {
                            productsBeanArrayList.add(product.getProducts().get(i));
                        }
                        bundle.putParcelableArrayList("product", productsBean);

                        initData();
                    }
                });
            }
        }).start();
    }

    private void initData() {
        classificationAdapter = new ClassificationAdapter(getSupportFragmentManager());
        viewPager.setAdapter(classificationAdapter);
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public class ClassificationAdapter extends FragmentStatePagerAdapter {

        public ClassificationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new ClassificationFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
