package com.mvc.dresssup;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.fragment.KefuFragment;
import com.mvc.dresssup.fragment.MyFragment;
import com.mvc.dresssup.fragment.OrderFragment;
import com.mvc.dresssup.view.NoScroolViewPager;
import com.mvc.dresssup.view.PreViewPager;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mvc.dresssup.factory.FragmentFacatory.createFragment;


public class MainActivity extends FragmentActivity {

    @BindView(R.id.vp_content)
    NoScroolViewPager vpContent;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_order)
    RadioButton rbOrder;
    @BindView(R.id.rb_kefu)
    RadioButton rbKefu;
    @BindView(R.id.rb_setting)
    RadioButton rbSetting;
    @BindView(R.id.rgGroup)
    RadioGroup rgGroup;
    private MyOnPageChangeListener listener;
    private int width;
    private OrderFragment orderFragment;
    private KefuFragment kefuFragment;
    private MyFragment myFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListener();
        DecimalFormat df = new DecimalFormat("0");
        String format = df.format(6.5);
        String format1 = df.format(7.5);
        System.out.println(format+format1);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    private void initListener() {

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vpContent.setCurrentItem(0, false);
                        break;
                    case R.id.rb_order:
                        vpContent.setCurrentItem(1, false);
                        break;
                    case R.id.rb_kefu:
                        vpContent.setCurrentItem(2, false);
                        break;
                    case R.id.rb_setting:
                        vpContent.setCurrentItem(3, false);
                        break;
                }
            }
        });

        vpContent.setAdapter(new MainFragmentStaticPagerAdapter(getSupportFragmentManager()));
        listener = new MyOnPageChangeListener();
        vpContent.setOnPageChangeListener(listener);
        vpContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onPageSelected(0);
                vpContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });


    }


    public class MainFragmentStaticPagerAdapter extends FragmentStatePagerAdapter {

        public MainFragmentStaticPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                Fragment fragment = createFragment(position);
                return fragment;
            }else if(position==1){
                orderFragment = new OrderFragment();
                return orderFragment;
            }else if(position==2){
                kefuFragment = new KefuFragment();
                return kefuFragment;
            }else if(position==3){
                myFragment = new MyFragment();
                return myFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public class MyOnPageChangeListener implements  PreViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 页面被选中时调用方法,数据应该在此时加载
         * @param position 第几页数据
         */
        @Override
        public void onPageSelected(int position) {
            if(position==0) {
                //从工厂中取出缓存的fragment
                BaseFragment baseFragment = createFragment(position);
                //取出LoadingPager对象
                LoadingPager loadingPager = baseFragment.getLoadingPager();
                //调用LoadingPager里面的触摸方法
                loadingPager.triggerLoadData();
            }else if(position==1){
                LoadingPager loadingPager1 =  orderFragment.getLoadingPager();
                loadingPager1.triggerLoadData();
            }else if(position==2){
                LoadingPager loadingPager = kefuFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            }else if(position==3){
                LoadingPager loadingPager =myFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
