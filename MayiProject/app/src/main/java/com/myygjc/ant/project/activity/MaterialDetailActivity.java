package com.myygjc.ant.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.db.DatabaseManager;
import com.myygjc.ant.project.doman.MessageEvent;
import com.myygjc.ant.project.doman.ReSearch;
import com.myygjc.ant.project.fragment.MaterialDetailFragment;
import com.myygjc.ant.project.util.UIUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/1/13.
 */

public class MaterialDetailActivity extends FragmentActivity {
    @Bind(R.id.vp_detail)
    ViewPager vpDetail;
    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.tv_hint)
    EditText tvHint;
    @Bind(R.id.tv_search)
    public TextView tvSearch;
    private MaterialDetailFragment fragment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        bundle = new Bundle();
        bundle.putString("name", name);
        setContentView(R.layout.activity_materialdetail);
        ButterKnife.bind(this);
        tvHint.setText(name);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvHint.getText().toString().equals("")){
                    Toast.makeText(UIUtils.getContext(),"搜索内容不可为空",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(fragment.getLoadingPager().mCurState!=2){
                    bundle.putString("name", tvHint.getText().toString());
                    initData();
                }else {
                    search();
                }
            }
        });
        initData();


    }

    private void initData() {
        vpDetail.setAdapter(new MaterialDetailAdapter(getSupportFragmentManager()));
        vpDetail.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                vpDetail.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageEvent messageEvent = new MessageEvent();
                EventBus.getDefault().post(messageEvent);
                finish();
            }
        });


        tvHint.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(tvHint.getText().toString().equals("")){
                    Toast.makeText(UIUtils.getContext(),"搜索内容不可为空",Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (event != null) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        if (fragment.getLoadingPager().mCurState != 2) {
                            bundle.putString("name", tvHint.getText().toString());
                            initData();
                        } else {
                            search();
                        }
                    }
                        return true;
                    }
                    return false;

                }

        });
    }

    private void search() {
        String tvName = tvHint.getText().toString();
        DatabaseManager manager = new DatabaseManager(UIUtils.getContext());
        manager.insert(tvName);
        ReSearch messageEvent=new ReSearch();
        messageEvent.setTvNama(tvName);
        EventBus.getDefault().post(messageEvent);
    }

    public class MaterialDetailAdapter extends FragmentStatePagerAdapter {

        public MaterialDetailAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new MaterialDetailFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }


}
