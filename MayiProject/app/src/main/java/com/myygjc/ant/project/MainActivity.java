package com.myygjc.ant.project;

import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.fragment.BuyFragment;
import com.myygjc.ant.project.fragment.SettingFragment;
import com.myygjc.ant.project.fragment.WorkHomeFragment;
import com.myygjc.ant.project.view.NoScroolViewPager;
import com.myygjc.ant.project.view.PreViewPager;
import com.myygjc.ant.project.view.WowSplashView;
import com.myygjc.ant.project.view.WowView;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.myygjc.ant.project.factory.FragmentFacatory.createFragment;


public class MainActivity extends FragmentActivity {


    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_gov)
    RadioButton rbGov;
    @Bind(R.id.rb_setting)
    RadioButton rbSetting;
    @Bind(R.id.rgGroup)
    RadioGroup rgGroup;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.vp_content)
    NoScroolViewPager vpContent;
    @Bind(R.id.wowSplash)
    WowSplashView wowSplash;
    @Bind(R.id.wowView)
    WowView wowView;
    private long mkeyTime;
    private WorkHomeFragment fragment;
    private MyOnPageChangeListener listener;
    private BuyFragment buyFragment;
    private SettingFragment settingFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        wowSplash.startAnimate();
        wowSplash.setOnEndListener(new WowSplashView.OnEndListener() {
            @Override
            public void onEnd(WowSplashView wowSplashView) {
                wowSplashView.setVisibility(View.GONE);
                wowView.setVisibility(View.VISIBLE);
                wowView.startAnimate(wowSplashView.getDrawingCache());
            }
        });
        //初始化监听
        initListener();

    }


    private void initListener() {

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        vpContent.setCurrentItem(0, false);
                        break;
                    case R.id.rb_buy:
                        vpContent.setCurrentItem(1, false);
                        break;
                    case R.id.rb_gov:
//                        vpContent.setAdapter(new MainFragmentStaticPagerAdapter(getSupportFragmentManager()));
                        //                      new MainFragmentStaticPagerAdapter(getSupportFragmentManager());
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
            if (position == 0) {
                Fragment fragment = createFragment(position);
                return fragment;
            } else if (position == 2) {
                fragment = new WorkHomeFragment();
                return fragment;
            } else if (position == 1) {
                buyFragment = new BuyFragment();
                return buyFragment;
            } else if (position == 3) {
                settingFragment = new SettingFragment();
                return settingFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public class MyOnPageChangeListener implements PreViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 页面被选中时调用方法,数据应该在此时加载
         *
         * @param position 第几页数据
         */
        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                //从工厂中取出缓存的fragment
                BaseFragment baseFragment = createFragment(position);
                //取出LoadingPager对象
                LoadingPager loadingPager = baseFragment.getLoadingPager();
                //调用LoadingPager里面的触摸方法
                loadingPager.triggerLoadData();
            } else if (position == 2) {
                LoadingPager loadingPager1 = fragment.getLoadingPager();
                loadingPager1.triggerLoadData();
            } else if (position == 1) {
                LoadingPager loadingPager = buyFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            } else if (position == 3) {
                LoadingPager loadingPager = settingFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            } else {
                Process.killProcess(Process.myPid());
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mkeyTime) > 2000) {
            mkeyTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
        } else {
            Process.killProcess(Process.myPid());
        }
    }

}
