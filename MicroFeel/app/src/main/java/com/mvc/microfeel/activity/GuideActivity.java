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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mvc.microfeel.MainActivity;
import com.mvc.microfeel.R;
import com.mvc.microfeel.base.BaseFragment;
import com.mvc.microfeel.base.LoadingPager;
import com.mvc.microfeel.factory.FragmentGuideFacatory;
import com.mvc.microfeel.util.PrefUtils;
import com.mvc.microfeel.util.UIUtils;

import java.util.ArrayList;

public class GuideActivity extends FragmentActivity {
    private ViewPager vp_guide;
    private LinearLayout ll_content;
    private ImageView iv_point_selector;
    private int[]imageIds=new int[]{R.drawable.catx,R.drawable.catx2,R.drawable.catx3};
    private ArrayList<ImageView>imageviews;
    private int pointDis;
    private Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //是否第一次进引导页
        if(PrefUtils.getBoolean(UIUtils.getContext(),"isfirstGuide",false)){
            //是否第一次登陆
            if(PrefUtils.getBoolean(UIUtils.getContext(),"isLogin",false)){
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }else{
                startActivity(new Intent(GuideActivity.this,LoginActivity.class));
                finish();
            }
        }
        initView();
        initData();
    }
    private void initView() {
        vp_guide= (ViewPager) findViewById(R.id.vp_guide);
        ll_content= (LinearLayout) findViewById(R.id.ll_content);
        iv_point_selector= (ImageView) findViewById(R.id.iv_point_selector);
        btnStart= (Button) findViewById(R.id.btnStart);
    }
    //初始化数据
    private void initData() {
        imageviews=new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView iv=new ImageView(this);
            iv.setBackgroundResource(imageIds[i]);
            imageviews.add(iv);
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(R.drawable.shape_point_nomal);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if(i>0){
                params.leftMargin=10;
            }
            imageView.setLayoutParams(params);
            ll_content.addView(imageView);
        }
        vp_guide.setAdapter(new Myadapter(getSupportFragmentManager()));
        vp_guide.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onPageSelected(0);
                vp_guide.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        vp_guide.setOnPageChangeListener(listener);
        //监听这个红点的图片什么时候画完
        iv_point_selector.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //两个点之间的距离
                pointDis = ll_content.getChildAt(1).getLeft() - ll_content.getChildAt(0).getLeft();
                iv_point_selector.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        //设置这个点击事件监听事件
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PrefUtils.getBoolean(UIUtils.getContext(),"isLogin",false)){
                    startActivity(new Intent(GuideActivity.this,MainActivity.class));
                     finish();
                    PrefUtils.putBoolean(UIUtils.getContext(),"isfirstGuide",true);
                }else {
                    startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                    finish();
                    PrefUtils.putBoolean(UIUtils.getContext(), "isfirstGuide", true);
                }
            }
        });
    }

//    class Myadapter extends PagerAdapter {
//        //数据
//        @Override
//        public int getCount() {
//            return imageviews.size();
//        }
//        //复用
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view==object;
//        }
//        //创建视图
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            ImageView iv=imageviews.get(position);
//            container.addView(iv);
//            return iv;
//        }
//        //销毁视图
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//    }

    public class  Myadapter extends FragmentStatePagerAdapter {

        public  Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = FragmentGuideFacatory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    //设置一个viewpager的监听器
    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        //当页面滑动时调用的方法
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //position当前控件位置,positionOffset百分比,从0-->0.99-->0
            int leftMargin = (int) (positionOffset * pointDis + position * pointDis);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_point_selector.getLayoutParams();
            layoutParams.leftMargin = leftMargin;
            iv_point_selector.setLayoutParams(layoutParams);
        }

        //页面被选中调用的方法
        @Override
        public void onPageSelected(int position) {
            BaseFragment baseFragment = FragmentGuideFacatory.createFragment(position);
            //取出LoadingPager对象
            LoadingPager loadingPager = baseFragment.getLoadingPager();
            //调用LoadingPager里面的触摸方法
            loadingPager.triggerLoadData();
            if (position == imageIds.length - 1) {
                btnStart.setVisibility(View.VISIBLE);
            } else {
                btnStart.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
