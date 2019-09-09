package com.myygjc.ant.project.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.EleSearchActivity;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.factory.FragmentClassifyFacatory;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.UIUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zy2 on 2016/12/27.
 */

public class HomeFragment extends BaseFragment {


    @Bind(R.id.tv_search_bg)
    TextView tvSearchBg;
    @Bind(R.id.magic_indicator1)
    MagicIndicator magicIndicator1;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    String[] mDataList = {"水", "电", "木", "瓦", "油", "五金", "劳保", "其他"};


    private OnPageChangeListener listener;
    private BaseFragment fragment;

    @Override
    public LoadingPager.LoadedResult initData() {
        return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_search, null);
        ButterKnife.bind(this, view);
        tvSearchBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EleSearchActivity.class);
                startActivity(intent);
            }
        });
        viewPager.setAdapter(new ClassifyFragmentAdapter(getActivity().getSupportFragmentManager()));
        listener = new OnPageChangeListener();
        viewPager.setOnPageChangeListener(listener);
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onPageSelected(0);
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        System.out.println(DesUtils.encryptDes("0"));
        initMagicIndicator1();
        return view;
    }


    class ClassifyFragmentAdapter extends FragmentStatePagerAdapter {

        public ClassifyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = FragmentClassifyFacatory.createFragment(position);

            return fragment;
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDataList[position];
        }
    }


    public class OnPageChangeListener implements ViewPager.OnPageChangeListener {

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
            BaseFragment fragment = FragmentClassifyFacatory.createFragment(position);
            LoadingPager loadingPager = fragment.getLoadingPager();
            loadingPager.triggerLoadData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initMagicIndicator1() {

        magicIndicator1.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(UIUtils.getContext());
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                final int[] ints = {R.drawable.x_shuigong, R.drawable.x_diangong, R.drawable.x_mugong, R.drawable.x_wagong, R.drawable.x_yougong, R.drawable.wujin, R.drawable.x_wujin, R.drawable.other};
                // load custom layout
                View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
                final ImageView titleImg = (ImageView) customLayout.findViewById(R.id.title_img);
                final TextView titleText = (TextView) customLayout.findViewById(R.id.title_text);
                titleImg.setImageResource(ints[index]);
                titleText.setText(mDataList[index]);
                commonPagerTitleView.setContentView(customLayout);

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(Color.BLACK);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(Color.LTGRAY);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                        titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
                        titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
                        titleImg.setImageResource(ints[index]);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                        titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
                        titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
                        int[] ints = {R.drawable.x_shuigong_hl, R.drawable.x_diangong_hl, R.drawable.x_mugong_hl, R.drawable.x_wagong_hl, R.drawable.x_yougong_hl, R.drawable.wujin1, R.drawable.x_wujin_hl, R.drawable.other1};
                        titleImg.setImageResource(ints[index]);
                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#FFD700"));
                return indicator;
            }
        });
        magicIndicator1.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator1, viewPager);
    }
}




