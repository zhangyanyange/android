package com.microfeel.meiquetiliao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.adapter.MaterialzAdapter;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.base.MyApplication;
import com.microfeel.meiquetiliao.db.ShopCartManager;
import com.microfeel.meiquetiliao.domain.LimitMoney;
import com.microfeel.meiquetiliao.domain.Material;
import com.microfeel.meiquetiliao.domain.ShopCat;
import com.microfeel.meiquetiliao.procotol.GetMaterialProcotol;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.ShowToastTime;
import com.microfeel.meiquetiliao.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MaterialActivity extends Activity {

    @Bind(R.id.loadView)
    ProgressBar loadView;
    @Bind(R.id.lvClassis)
    ListView lvClassis;
    @Bind(R.id.back)
    ImageButton back;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.ib_shopcart)
    ImageButton ibShopcart;
    private Material material;
    private int id;
    private MaterialzAdapter adapter;
    private ShopCartManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        String title = intent.getStringExtra("title");
       id = intent.getIntExtra("id", 0);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (title == null) {
            tvTitleName.setText("所有材料");
        } else {
            tvTitleName.setText(title);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadView.setVisibility(View.VISIBLE);
                    }
                });
                material = GetMaterialProcotol.getMaterial(id);
                if (material != null) {
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new MaterialzAdapter(MaterialActivity.this, material.getListContent());
                            lvClassis.setAdapter(adapter);

                            adapter.setOnAddCartListener(new MaterialzAdapter.OnAddCartListener() {
                                @Override
                                public void addCart(int position) {
                                    manager = new ShopCartManager(MaterialActivity.this);
                                    Material.ListContentBean bean = material.getListContent().get(position);
                                    boolean projectId = manager.insert(bean.getName(), 1, bean.getPrice(), bean.getUnit(), bean.getID() + "", PrefUtils.getString(UIUtils.getContext(), "projectId", ""));

                                    if (projectId) {
                                        ShowToastTime.showTextToast(bean.getName()+"添加成功");
                                    }
                                }
                            });
                        }
                    });

                } else {
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MaterialActivity.this, "列表为空", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadView.setVisibility(View.GONE);
                    }
                });
            }
        }).start();


        lvClassis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!material.getListContent().get(i).isIsDetail()) {
                    Intent intent = new Intent(UIUtils.getContext(), MaterialActivity.class);
                    intent.putExtra("title", material.getListContent().get(i).getName());
                    intent.putExtra("id", material.getListContent().get(i).getID());
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });

        ibShopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager = new ShopCartManager(MaterialActivity.this);
                ArrayList<ShopCat> projectId = manager.query(PrefUtils.getString(UIUtils.getContext(), "projectId", ""));
                if(projectId.size()==0){
                    Toast.makeText(MaterialActivity.this, "请添加材料", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    System.out.println(PrefUtils.getString(UIUtils.getContext(), "projectId", "")+"/"+ PrefUtils.getString(UIUtils.getContext(), "location",""));
                    ibShopcart.setEnabled(false);
                    loadView.setVisibility(View.VISIBLE);
                    OkHttpUtils
                            .get()
                            .url(Constants.BASE_URL+"Project/GetLimitAmount/"+ PrefUtils.getString(UIUtils.getContext(), "projectId", "")+"/"+ PrefUtils.getString(UIUtils.getContext(), "location",""))
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                    System.out.println(e);
                                    ibShopcart.setEnabled(true);
                                    loadView.setVisibility(View.GONE);
                                    Toast.makeText(MaterialActivity.this, "请稍后尝试,获取限额失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response) {
                                    System.out.println(response);
                                    ibShopcart.setEnabled(true);
                                    Gson gson=new Gson();
                                    LimitMoney limitMoney = gson.fromJson(response, LimitMoney.class);
                                    loadView.setVisibility(View.GONE);
                                    Intent intent1=new Intent(UIUtils.getContext(),ShopCartActivity.class);
                                    intent1.putExtra("limitMoney",limitMoney.getStringContent());
                                    startActivity(intent1);
                                }
                            });
                }
            }
        });
    }
}
