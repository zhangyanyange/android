package com.microfeel.meiquetiliao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.adapter.MaterialAdapter;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.domain.MaterialDetail;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ShippedDetailActivity extends Activity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.lv_material)
    ListView lvMaterial;
    @Bind(R.id.tv_sure)
    TextView tvSure;
    @Bind(R.id.loadView)
    ProgressBar loadView;
    @Bind(R.id.activity_material_detail)
    RelativeLayout activityMaterialDetail;
    @Bind(R.id.ll_detail)
    LinearLayout llDetail;
    @Bind(R.id.tv__lookphoto)
    TextView tvLookPhoto;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);

        gson = new Gson();
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final String orderid = intent.getStringExtra("orderid");

        String time = intent.getStringExtra("time");
        tvLookPhoto.setVisibility(View.GONE);
        tvName.setText(PrefUtils.getString(UIUtils.getContext(),"customerName",""));
        tvPhone.setText(PrefUtils.getString(UIUtils.getContext(),"customerPhone","").substring(0,11));
        tvLocation.setText(PrefUtils.getString(UIUtils.getContext(),"location",""));
        if(time.contains("9999")){
            tvTime.setText("");
        }else{
            tvTime.setText(time.substring(0,10));
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
               OkHttpUtils.get().url(Constants.BASE_URL+"order/GetfinishOrderdetails?orderid="+orderid).build().execute(new StringCallback() {
                   @Override
                   public void onError(Call call, Exception e) {

                   }

                   @Override
                   public void onResponse(String response) {
                     MaterialDetail materialDetail = gson.fromJson(response,MaterialDetail.class);
                     MaterialAdapter adapter=new MaterialAdapter(ShippedDetailActivity.this,materialDetail.getListContent(),3);
                     lvMaterial.setAdapter(adapter);
                   }
               });
            }
        }).start();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSure.setVisibility(View.GONE);
    }


}
