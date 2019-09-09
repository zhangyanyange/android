package com.myygjc.ant.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.doman.MessageEvent;
import com.myygjc.ant.project.fragment.MaterialAllFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaterialAllActivity extends FragmentActivity {

    @Bind(R.id.back)
    ImageButton back;
    @Bind(R.id.vp_material)
    ViewPager vpMaterial;
    private MaterialAllFragment fragment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_all);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        bundle = new Bundle();
        bundle.putInt("id", id);
        initData();
    }

    private void initData() {
        vpMaterial.setAdapter(new MaterialAllAdapter(getSupportFragmentManager()));
        vpMaterial.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LoadingPager loadingPager = fragment.getLoadingPager();
                loadingPager.triggerLoadData();
                vpMaterial.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }
    public class MaterialAllAdapter extends FragmentStatePagerAdapter {

        public MaterialAllAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = new MaterialAllFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(MessageEvent messageEvent) {
        finish();
    }
}
