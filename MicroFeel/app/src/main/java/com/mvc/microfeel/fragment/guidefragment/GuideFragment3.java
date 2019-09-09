package com.mvc.microfeel.fragment.guidefragment;

import android.view.View;

import com.mvc.microfeel.R;
import com.mvc.microfeel.base.BaseFragment;
import com.mvc.microfeel.base.LoadingPager;
import com.mvc.microfeel.util.UIUtils;

/**
 * Created by zy2 on 2017/8/16.
 */

public class GuideFragment3  extends BaseFragment{
    @Override
    public LoadingPager.LoadedResult initData() {
        return LoadingPager.LoadedResult.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_guide3, null);
        return view;
    }
}
