package com.myygjc.ant.project.factory;


import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.fragment.BuyFragment;
import com.myygjc.ant.project.fragment.HomeFragment;
import com.myygjc.ant.project.fragment.SettingFragment;
import com.myygjc.ant.project.fragment.WorkHomeFragment;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by aus on 2016/10/18.
 */
public class FragmentFacatory {
    public static final int FRAGMENT_HOME = 0;//首页
    public static final int FRAGMENT_BUY = 1;//购买中心
    public static final int FRAGMENT_WorkHome = 2;//工作中心
    public static final int FRAGMENT_Setting = 3;//设置


    //将这些fragment放到集合中,可以优化减少对网路访问
    //主要目的是为了拿到loadingpager,调用改变触摸加载数据方法

    public static Map<Integer, BaseFragment> mChaceFragment=new HashMap<>();



    public static BaseFragment createFragment(int position) {
        BaseFragment fragment=null;

        //优先从集合中拿出所需要的数据
        if(mChaceFragment.containsKey(position)) {
            fragment = mChaceFragment.get(position);
            //System.out.println("从我这拿走fragment了");
            return fragment;
        }
        switch (position){
            case FRAGMENT_HOME:
                fragment=new HomeFragment();
                break;
            case FRAGMENT_BUY:
                fragment=new BuyFragment();
                break;
            case FRAGMENT_WorkHome:
                fragment=new WorkHomeFragment();
                break;
            case FRAGMENT_Setting:
                fragment=new SettingFragment();
                break;

            //将数据加入到集合中
        }
      mChaceFragment.put(position,fragment);
        return fragment;
    }
}
