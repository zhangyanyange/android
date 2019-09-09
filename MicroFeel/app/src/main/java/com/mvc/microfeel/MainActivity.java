package com.mvc.microfeel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewTreeObserver;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mvc.microfeel.base.LoadingPager;
import com.mvc.microfeel.domain.Logout;
import com.mvc.microfeel.fragment.HomeFragment;
import com.mvc.microfeel.fragment.SettingFragment;
import com.mvc.microfeel.view.NoScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_setting)
    RadioButton rbSetting;
    @Bind(R.id.rgGroup)
    RadioGroup rgGroup;
    @Bind(R.id.vp_content)
    NoScrollView vpContent;

    private MyOnPageChangeListener listener;
    private HomeFragment baseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        ButterKnife.bind(this);
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
                    case R.id.rb_setting:
                        vpContent.setCurrentItem(1, false);
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
                baseFragment = new HomeFragment();
                return baseFragment;
            }
            SettingFragment fragment=new SettingFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

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
            if(position==0){
//               HomeFragment baseFragment=new HomeFragment();
                //取出LoadingPager对象
                LoadingPager loadingPager = baseFragment.getLoadingPager();
                //调用LoadingPager里面的触摸方法
                loadingPager.triggerLoadData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(Logout noLogin) {
       finish();
    }
}
