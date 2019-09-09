package com.mvc.microfeel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mvc.microfeel.R;
import com.mvc.microfeel.domain.Logout;
import com.mvc.microfeel.util.PrefUtils;
import com.mvc.microfeel.util.UIUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UniversallyActivity extends Activity {

    @Bind(R.id.back)
    ImageButton back;
    @Bind(R.id.creal_cache)
    RelativeLayout crealCache;
    @Bind(R.id.logout)
    RelativeLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universally);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        crealCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UniversallyActivity.this, "缓存以被清理", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UniversallyActivity.this,LoginActivity.class));
                EventBus.getDefault().post(new Logout());
                PrefUtils.putBoolean(UIUtils.getContext(),"isLogin",false);
                finish();
            }
        });
    }
}
