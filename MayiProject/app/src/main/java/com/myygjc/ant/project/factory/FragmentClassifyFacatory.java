package com.myygjc.ant.project.factory;


import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.fragment.ClassifyFragment.ClassifyFragment1;
import com.myygjc.ant.project.fragment.ClassifyFragment.ClassifyFragment2;
import com.myygjc.ant.project.fragment.ClassifyFragment.ClassifyFragment3;
import com.myygjc.ant.project.fragment.ClassifyFragment.ClassifyFragment4;
import com.myygjc.ant.project.fragment.ClassifyFragment.ClassifyFragment5;
import com.myygjc.ant.project.fragment.ClassifyFragment.ClassifyFragment6;
import com.myygjc.ant.project.fragment.ClassifyFragment.ClassifyFragment7;
import com.myygjc.ant.project.fragment.ClassifyFragment.ClassifyFragment8;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by aus on 2016/10/18.
 */
public class FragmentClassifyFacatory {
    public static final int FRAGMENT_1 = 0;//首页
    public static final int FRAGMENT_2 = 1;//购买中心
    public static final int FRAGMENT_3 = 2;//工作中心
    public static final int FRAGMENT_4 = 3;//设置
    public static final int FRAGMENT_5 = 4;//首页
    public static final int FRAGMENT_6 = 5;//购买中心
    public static final int FRAGMENT_7 = 6;//工作中心
    public static final int FRAGMENT_8 = 7;//设置


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
            case FRAGMENT_1:
                fragment=new ClassifyFragment1();
                break;
            case FRAGMENT_2:
                fragment=new ClassifyFragment2();
                break;
            case FRAGMENT_3:
                fragment=new ClassifyFragment3();
                break;
            case FRAGMENT_4:
                fragment=new ClassifyFragment4();
                break;
            case FRAGMENT_5:
                fragment=new ClassifyFragment5();
                break;
            case FRAGMENT_6:
                fragment=new ClassifyFragment6();
                break;
            case FRAGMENT_7:
                fragment=new ClassifyFragment7();
                break;
            case FRAGMENT_8:
                fragment=new ClassifyFragment8();
                break;

            //将数据加入到集合中
        }
      mChaceFragment.put(position,fragment);
        return fragment;
    }
}
