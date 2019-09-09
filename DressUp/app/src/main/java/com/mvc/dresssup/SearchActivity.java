package com.mvc.dresssup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvc.dresssup.util.UIUtils;
import com.mvc.dresssup.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/9/6.
 */

public class SearchActivity extends Activity {

    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.tv_search_bg)
    TextView tvSearchBg;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_hint)
    EditText tvHint;
    @BindView(R.id.frame_bg)
    FrameLayout frameBg;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.rl_history)
    RelativeLayout rlHistory;
    @BindView(R.id.ll_history)
    FlowLayout llHistory;
    @BindView(R.id.rv_search)
    FlowLayout rvSearch;
    @BindView(R.id.frame_content_bg)
    LinearLayout frameContentBg;
    private Bundle bundle;

    private boolean finishing;


    private ArrayList<String> data = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    String[] remen = {"伟星", "龙彩", "西卡", "绿时代", "立邦", "胖子五金", "玉桐"};


    //创建一个list集合，用来存放历史纪录
    private List<String> history = new ArrayList<>();


    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        for (int i = 0; i < remen.length; i++) {
            data.add(remen[i]);
            history.add(remen[i]);
        }

        /**
         * 查询是否有数据动态添加数据
         */
        names.clear();
        for (int i = 0; i < 7; i++) {
            String name = data.get(i);
            names.add(name);
        }
        refreshHestory();

        /**
         * 添加textview
         */
//        llHistory.removeAllViews();
        for (int i = 0; i < names.size(); i++) {
            String data = names.get(i);
            TextView tv = getTextView(data);
            rvSearch.addView(tv);

        }

//        ibDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                refreshHestory();
//            }
//        });
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!finishing) {
            finishing = true;
        }
        finish();
    }


    @NonNull
    private TextView getTextView(final String data) {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(UIUtils.dip2px(13), UIUtils.dip2px(7), UIUtils.dip2px(13), UIUtils.dip2px(7));
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(17);
        tv.setText(data);

        /****给文本设置默认的背景图片颜色****/
        //GradientDrawable这个类就是用来在代码里给文本设置背景的,默认背景颜色
        GradientDrawable normalDg = new GradientDrawable();
        //设置这个图片的应该有多大弧度
        normalDg.setCornerRadius(UIUtils.dip2px(7));

        int alpha = 255;
        int red = 248;
        int green = 248;
        int blue = 255;
        int argb = Color.argb(alpha, red, green, blue);
        normalDg.setColor(argb);

        /**按下背景颜色**/
        GradientDrawable pressDg = new GradientDrawable();
        //设置这个图片的应该有多大弧度
        pressDg.setCornerRadius(UIUtils.dip2px(7));

        pressDg.setColor(Color.LTGRAY);

        /**背景选择器,相当于资源文件中selector**/
        StateListDrawable selectDg = new StateListDrawable();
        /**添加2个状态,按压状态在前**/
        selectDg.addState(new int[]{android.R.attr.state_pressed}, pressDg);
        /**在android中-代表不是这个状态**/
        selectDg.addState(new int[]{-android.R.attr.state_pressed}, normalDg);
        //设置背景图片
        tv.setBackgroundDrawable(selectDg);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intent = new Intent(UIUtils.getContext(), MaterialDetailActivity.class);
//                intent.putExtra("name", data);
//                startActivity(intent);
            }
        });
        return tv;
    }


    /**
     * 增加刷新ui的逻辑
     */
    public void refreshHestory() {
//        history.clear();

        if (names.size() != 0) {
            rlHistory.setVisibility(View.VISIBLE);
            /******************动态添加代码************************/
            llHistory.removeAllViews();
            /**
             * 动态添加历史纪录
             */
            for (int i = names.size() - 1; i >= 0; i--) {
                final String data = names.get(i);
                TextView tv = getTextView(data);
                llHistory.addView(tv);
            }
        } else {
            llHistory.removeAllViews();
            rlHistory.setVisibility(View.INVISIBLE);
        }
        /******************动态添加代码************************/
    }
}
