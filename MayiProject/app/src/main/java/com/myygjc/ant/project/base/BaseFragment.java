package com.myygjc.ant.project.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.myygjc.ant.project.util.UIUtils;

import java.util.List;
import java.util.Map;


/**
 * Created by zhangyan
 */
public abstract class BaseFragment extends Fragment {


    private LoadingPager mLoadingPager;

    /**
     * @return 返回这个类
     */
    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    /**
     * 此方法返回fragment所需要的视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(UIUtils.getContext()) {
                @Override
                public LoadedResult initData() {
                    return BaseFragment.this.initData();
                }

                @Override
                public View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }
            };
        } else {
            ViewParent parent = mLoadingPager.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mLoadingPager);
            }
        }
        //加载完数据只够调用此方法
        //mLoadingPager.triggerLoadData();
        return mLoadingPager;
    }

    /**
     * basefragment不知该怎么做交给子类去完成
     *
     * @return
     */

    public abstract LoadingPager.LoadedResult initData();

    public abstract View initSuccessView();

    public LoadingPager.LoadedResult checkResult(Object objRes) {
        if (objRes == null) {
            return LoadingPager.LoadedResult.ERROR;
        }

        if (objRes instanceof List) {
            if (((List) objRes).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        if (objRes instanceof Map) {
            if (((Map) objRes).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        return LoadingPager.LoadedResult.SUCCESS;
    }
}
