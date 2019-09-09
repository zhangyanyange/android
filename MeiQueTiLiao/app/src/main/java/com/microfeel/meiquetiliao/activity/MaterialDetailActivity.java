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
import android.widget.Toast;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.adapter.MaterialAdapter;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.domain.ChangeTab;
import com.microfeel.meiquetiliao.domain.LimitMoney;
import com.microfeel.meiquetiliao.domain.MaterialDetail;
import com.microfeel.meiquetiliao.domain.OrderTijiao;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.ShowToastTime;
import com.microfeel.meiquetiliao.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

public class MaterialDetailActivity extends Activity {

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
    private String tuiliao;
    private MaterialDetail materialDetail;

    private double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);

        gson = new Gson();
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        final String code = intent.getStringExtra("Code");
        String tijiao = intent.getStringExtra("tijiao");

        tuiliao = intent.getStringExtra("tuiliao");

        String time = intent.getStringExtra("time");
        tvName.setText(PrefUtils.getString(UIUtils.getContext(),"customerName",""));
        tvPhone.setText(PrefUtils.getString(UIUtils.getContext(),"customerPhone","").substring(0,11));
        tvLocation.setText(PrefUtils.getString(UIUtils.getContext(),"location",""));
        tvTime.setText(time.substring(0,10));
        if(!tuiliao.equals("")){
            tvLookPhoto.setVisibility(View.VISIBLE);
        }else{
            tvLookPhoto.setVisibility(View.GONE);
        }

        tvLookPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MaterialDetailActivity.this,PhotoViewActivity.class);
                intent1.putExtra("photo",tuiliao.split("---")[2]);
                intent1.putExtra("Code",code);
                startActivity(intent1);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
               OkHttpUtils.get().url(Constants.BASE_URL+"Order?orderNumber="+code).build().execute(new StringCallback() {
                   @Override
                   public void onError(Call call, Exception e) {

                   }

                   @Override
                   public void onResponse(String response) {
                       materialDetail = gson.fromJson(response,MaterialDetail.class);
                       MaterialAdapter adapter=new MaterialAdapter(MaterialDetailActivity.this, materialDetail.getListContent(),2);
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

        if (tijiao.equals("1")) {
            tvSure.setVisibility(View.VISIBLE);
        } else {
            tvSure.setVisibility(View.GONE);
        }

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSure.setEnabled(false);
                loadView.setVisibility(View.VISIBLE);
                OkHttpUtils
                        .get()
                        .url(Constants.BASE_URL+"Project/GetLimitAmount/"+ PrefUtils.getString(UIUtils.getContext(), "projectId", "")+"/"+ PrefUtils.getString(UIUtils.getContext(), "location",""))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {
                                loadView.setVisibility(View.GONE);
                                tvSure.setEnabled(true);
                                Toast.makeText(MaterialDetailActivity.this, "请稍后尝试,获取限额失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response) {
                                LimitMoney limitMoney = gson.fromJson(response, LimitMoney.class);
                                double projectTotal = getProjectTotal();
                                if(projectTotal>=Double.parseDouble(limitMoney.getStringContent())){
                                    ShowToastTime.showTextToast("项目已超限额,请删除材料，重新提料");
                                }else{
                                    OkHttpUtils
                                            .postString()
                                            .url(Constants.BASE_URL + "Order/Commit/" + code)
                                            .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                            .content("")
                                            .build()
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onError(Call call, Exception e) {
                                                    Toast.makeText(MaterialDetailActivity.this, "确认订单失败", Toast.LENGTH_SHORT).show();
                                                    tvSure.setEnabled(true);
                                                }

                                                @Override
                                                public void onResponse(String response) {
                                                    tvSure.setEnabled(true);
                                                    OrderTijiao orderTijiao = gson.fromJson(response, OrderTijiao.class);
                                                    ChangeTab event = new ChangeTab();
                                                    if (orderTijiao.getResult() == 0) {
                                                        if(!tuiliao.equals("")){
                                                            Toast.makeText(UIUtils.getContext(), orderTijiao.getMessage(), Toast.LENGTH_SHORT).show();
                                                            event.setPosition(3);
                                                            EventBus.getDefault().post(event);
                                                            finish();
                                                        }else{
                                                            Toast.makeText(UIUtils.getContext(), orderTijiao.getMessage(), Toast.LENGTH_SHORT).show();
                                                            event.setPosition(1);
                                                            EventBus.getDefault().post(event);
                                                            finish();
                                                        }
                                                    }
                                                }
                                            });
                                }
                                loadView.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

    private double getProjectTotal(){
        total=0;
        List<MaterialDetail.ListContentBean> listContent = materialDetail.getListContent();
        for (int i = 0; i <listContent.size(); i++) {
            total+=listContent.get(i).getPrice()*listContent.get(i).getNumber();
        }
        return total;
    }
}
