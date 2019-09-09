package com.myygjc.ant.project.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.db.DatabaseManager;
import com.myygjc.ant.project.doman.MessageEvent;
import com.myygjc.ant.project.util.UIUtils;
import com.myygjc.ant.project.view.FlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/1/13.
 */

public class EleSearchActivity extends FragmentActivity {


    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.tv_search_bg)
    TextView tvSearchBg;
    @Bind(R.id.tv_hint)
    EditText tvHint;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.frame_bg)
    FrameLayout frameBg;
    @Bind(R.id.tv_history)
    TextView tvHistory;
    @Bind(R.id.ib_delete)
    ImageButton ibDelete;
    @Bind(R.id.rl_history)
    RelativeLayout rlHistory;
    @Bind(R.id.ll_history)
    FlowLayout llHistory;
    @Bind(R.id.rv_search)
    FlowLayout rvSearch;
    @Bind(R.id.frame_content_bg)
    LinearLayout frameContentBg;
    private Bundle bundle;

    private boolean finishing;


    private ArrayList<String> data = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    String[] remen = {"伟星", "龙彩", "西卡", "绿时代", "立邦", "胖子五金", "玉桐"};

    private DatabaseManager manager;

    //创建一个list集合，用来存放历史纪录
    private List<String> history = new ArrayList<>();
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        Intent intent = getIntent();


        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvHint.getText().toString().equals("")){
                    Toast.makeText(UIUtils.getContext(),"搜索内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                search();

            }
        });

        tvHint.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(tvHint.getText().toString().equals("")){
                    Toast.makeText(UIUtils.getContext(),"搜索内容不能为空",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (event != null) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        search();
                        return true;
                    }
                }
                return false;
            }
        });
        initData();
    }

    private void search() {
        String tvContent = tvHint.getText().toString();
        //TODO 做页面跳转获取材料信息
        DatabaseManager manager = new DatabaseManager(UIUtils.getContext());
        manager.insert(tvContent);
        Intent intent = new Intent(UIUtils.getContext(), MaterialDetailActivity.class);
        intent.putExtra("name", tvContent);
        startActivity(intent);
    }

    private void initData() {
        for (int i = 0; i < remen.length; i++) {
            data.add(remen[i]);
        }

        /**
         * 查询是否有数据动态添加数据
         */
        manager = new DatabaseManager(UIUtils.getContext());

        refreshHestory();


        names.clear();
        for (int i = 0; i < 7; i++) {
            String name = data.get(i);
            names.add(name);
        }

        /**
         * 添加textview
         */
        rvSearch.removeAllViews();
        for (int i = 0; i < names.size(); i++) {
            String data = names.get(i);
            TextView tv = getTextView(data);
            rvSearch.addView(tv);
        }

        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.delete();
                refreshHestory();
            }
        });
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(MessageEvent messageEvent) {
       refreshHestory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                intent = new Intent(UIUtils.getContext(), MaterialDetailActivity.class);
                intent.putExtra("name", data);
                startActivity(intent);
            }
        });
        return tv;
    }


    /**
     * 增加刷新ui的逻辑
     */
    public void refreshHestory() {
        history.clear();
        history = manager.query();
        if (history.size() != 0) {
            rlHistory.setVisibility(View.VISIBLE);
            /******************动态添加代码************************/
            llHistory.removeAllViews();
            /**
             * 动态添加历史纪录
             */
            for (int i = history.size() - 1; i >= 0; i--) {
                final String data = history.get(i);
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
