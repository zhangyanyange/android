package com.mvc.dresssup.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by zy2 on 2017/10/26.
 */

public class NoScroolGridView extends GridView{
    public NoScroolGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScroolGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
