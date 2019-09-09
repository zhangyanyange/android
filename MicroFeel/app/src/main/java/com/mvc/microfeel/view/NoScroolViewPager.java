package com.mvc.microfeel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by aus on 2016/10/2.
 */
public class NoScroolViewPager extends PreViewPager {
    public NoScroolViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScroolViewPager(Context context) {
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
