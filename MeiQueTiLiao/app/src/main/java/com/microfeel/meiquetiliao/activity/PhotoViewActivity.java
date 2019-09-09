package com.microfeel.meiquetiliao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.adapter.LookPhotoAdapter;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.util.DateUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoViewActivity extends Activity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.gridView)
    GridView gridView;


    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);
    @Bind(R.id.bg)
    ImageView mBg;
    @Bind(R.id.img)
    PhotoView mPhotoView;
    @Bind(R.id.parent)
    FrameLayout mParent;
    private Info mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String photo = intent.getStringExtra("photo");
        String code = intent.getStringExtra("Code");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        in.setDuration(300);
        out.setDuration(300);

        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        final String[] split = photo.split(",");
        final ArrayList<String>photos=new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            photos.add(Constants.Picture_URL1+code+"/"+split[i]);
        }
        LookPhotoAdapter adapter = new LookPhotoAdapter(PhotoViewActivity.this, photos);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PhotoView p = (PhotoView) view;
                mInfo = p.getInfo();

                Glide.with(PhotoViewActivity.this).load(photos.get(position)).signature(new StringSignature(DateUtils.GetNowTime())).into(mPhotoView);
                mBg.startAnimation(in);
                mBg.setVisibility(View.VISIBLE);
                mParent.setVisibility(View.VISIBLE);;
                mPhotoView.animaFrom(mInfo);
            }
        });

        mPhotoView.enable();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBg.startAnimation(out);
                mPhotoView.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                        mParent.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mParent.getVisibility() == View.VISIBLE) {
            mBg.startAnimation(out);
            mPhotoView.animaTo(mInfo, new Runnable() {
                @Override
                public void run() {
                    mParent.setVisibility(View.GONE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }


}
