package com.mvc.microfeel.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 提供禁止滑动功能的自定义ViewPager
 */
public class NoScrollView extends ViewPager {
    public NoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollView(Context context) {
        super(context);
    }
    //不拦截滑动事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

}