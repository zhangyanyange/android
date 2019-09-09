package com.microfeel.meiquetiliao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.db.ShopCartManager;
import com.microfeel.meiquetiliao.domain.ChangeTab;
import com.microfeel.meiquetiliao.domain.CreatOrder;
import com.microfeel.meiquetiliao.domain.MaterialPostBean;
import com.microfeel.meiquetiliao.domain.ShopCat;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

public class OrderInfoActivity extends Activity {

    @Bind(R.id.rl_sure)
    RelativeLayout rlSure;
    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.et_name)
    TextView etName;
    @Bind(R.id.et_phone)
    TextView etPhone;
    @Bind(R.id.et_sendlocation)
    TextView etSendlocation;
    @Bind(R.id.ll_location)
    LinearLayout llLocation;
    @Bind(R.id.et_remove)
    EditText etRemove;
    @Bind(R.id.et_sendtime)
    TextView etSendtime;
    @Bind(R.id.ll_sendtime)
    LinearLayout llSendtime;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.cb_self)
    CheckBox cbSelf;
    @Bind(R.id.cb_sfby)
    CheckBox cbSfby;
    @Bind(R.id.ll_sfby)
    LinearLayout llSfby;
    @Bind(R.id.et_seconddistance)
    EditText etSeconddistance;
    @Bind(R.id.ll_distance)
    LinearLayout llDistance;
    @Bind(R.id.rb_count1)
    CheckBox rbCount1;
    @Bind(R.id.rb_count2)
    CheckBox rbCount2;
    @Bind(R.id.et_countstair)
    EditText etCountstair;
    @Bind(R.id.ll_stair)
    LinearLayout llStair;
    @Bind(R.id.activity_userdetail)
    LinearLayout activityUserdetail;
    @Bind(R.id.loadView)
    ProgressBar loadView;
    private TimePickerView pvTime;

    private String ID = "00000000-0000-0000-0000-000000000000";
    private String time2;
    private MaterialPostBean materialPost;
    private String[] strings = {"禧龙店", "龙凤库", "泰山", "王岗库", "海城店"};

    private String cangku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        ButterKnife.bind(this);
        Date date1 = new Date();
        time2 = getTime(date1);
        etSendtime.setText(time2);
        materialPost = new MaterialPostBean();
        etName.setText(PrefUtils.getString(UIUtils.getContext(), "tv_name", ""));
        etPhone.setText(PrefUtils.getString(UIUtils.getContext(), "tv_phone", ""));
        etSendlocation.setText(PrefUtils.getString(UIUtils.getContext(), "location", ""));
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  绑定 Adapter到控件
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                cangku = strings[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                cangku = strings[0];
            }
        });

        llSendtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime = new TimePickerView(OrderInfoActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
                //控制时间范围
                Calendar calendar = Calendar.getInstance();
                pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
                pvTime.setTime(new Date());
                pvTime.setCyclic(false);
                pvTime.setCancelable(true);
                //时间选择后回调
                pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

                    @Override
                    public void onTimeSelect(Date date) {

                        String time1 = getTime(date);

                        int result = time1.compareTo(time2);
                        if (result >= 0) {
                            etSendtime.setText(time1);
                        }
                    }
                });
                //弹出时间选择器
                pvTime.show();
            }
        });


        cbSelf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbCount2.setChecked(false);
                    rbCount1.setChecked(false);
                    cbSfby.setChecked(false);
                    llDistance.setVisibility(View.GONE);
                    llStair.setVisibility(View.GONE);
                    llSfby.setVisibility(View.GONE);
                } else {
                    cbSfby.setChecked(true);
                    llDistance.setVisibility(View.VISIBLE);
                    llStair.setVisibility(View.VISIBLE);
                    llSfby.setVisibility(View.VISIBLE);
                }
            }
        });

        cbSfby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llDistance.setVisibility(View.VISIBLE);
                    llStair.setVisibility(View.VISIBLE);
                } else {
                    rbCount2.setChecked(false);
                    rbCount1.setChecked(false);
                    llDistance.setVisibility(View.GONE);
                    llStair.setVisibility(View.GONE);
                }
            }
        });


        rbCount1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbSelf.setChecked(false);
                    cbSfby.setChecked(true);
                    rbCount2.setChecked(false);
                } else {
                    cbSfby.setChecked(false);
                }
            }
        });
        rbCount2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbSelf.setChecked(false);
                    cbSfby.setChecked(true);
                    rbCount1.setChecked(false);
                } else {
                    cbSfby.setChecked(false);
                }
            }
        });

        rlSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendTime = etSendtime.getText().toString();
                if (sendTime.equals("")) {
                    Toast.makeText(OrderInfoActivity.this, "时间不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                String remark = etRemove.getText().toString();
                materialPost.setID(ID);
                materialPost.setProjectID(PrefUtils.getString(UIUtils.getContext(), "projectId", ""));
                materialPost.setCreaterID(ID);
                materialPost.setShippingCost(0);
                materialPost.setHShipCost(0);
                materialPost.setVShipCost(0);
                materialPost.setCreateTime(time2);
                materialPost.setSubmitTime(time2);
                materialPost.setCallBackTime(time2);
                materialPost.setPostTime(time2);
                materialPost.setRemark(cangku + "---" + remark);
                materialPost.setReceiver(PrefUtils.getString(UIUtils.getContext(), "tv_name", ""));
                materialPost.setContact(PrefUtils.getString(UIUtils.getContext(), "tv_phone", ""));
                materialPost.setAddress(etSendlocation.getText().toString());

                materialPost.setRequireTime(sendTime);
                materialPost.setIsSelfPick(cbSelf.isChecked());

                String seconddistance = etSeconddistance.getText().toString();

                final String CountTair = etCountstair.getText().toString();
                if (rbCount1.isChecked()) {
                    if (CountTair.equals("")) {
                        Toast.makeText(UIUtils.getContext(), "请填写电梯层数", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (seconddistance.equals("")) {
                        Toast.makeText(UIUtils.getContext(), "请填写二次倒运距离", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    materialPost.setShipDistance(Integer.parseInt(seconddistance));
                    materialPost.setLiftNum(Integer.parseInt(CountTair));
                    materialPost.setFloorNum(0);

                }

                if (rbCount2.isChecked()) {
                    if (CountTair.equals("")) {
                        Toast.makeText(UIUtils.getContext(), "请填写楼梯层数", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (seconddistance.equals("")) {
                        Toast.makeText(UIUtils.getContext(), "请填写二次倒运距离", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    materialPost.setShipDistance(Integer.parseInt(seconddistance));
                    materialPost.setFloorNum(Integer.parseInt(CountTair));
                    materialPost.setLiftNum(0);
                }
                if (!cbSelf.isChecked()) {
                    materialPost.setIsSelfPick(false);
                    if (cbSfby.isChecked()) {
                        if (!rbCount1.isChecked() && !rbCount2.isChecked()) {
                            Toast.makeText(UIUtils.getContext(), "请选择楼梯还是电梯", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        materialPost.setIsSelfPick(true);

                        materialPost.setShipDistance(0);
                        materialPost.setFloorNum(0);
                        materialPost.setLiftNum(0);
                    }
                } else {
                    materialPost.setIsSelfPick(true);

                    materialPost.setShipDistance(0);
                    materialPost.setFloorNum(0);
                    materialPost.setLiftNum(0);
                }

                final ShopCartManager manager = new ShopCartManager(OrderInfoActivity.this);
                ArrayList<ShopCat> shopCats = manager.query(PrefUtils.getString(UIUtils.getContext(), "projectId", ""));
                ArrayList<MaterialPostBean.OrderDetailBean> list = new ArrayList<>();
                for (int i = 0; i < shopCats.size(); i++) {
                    MaterialPostBean.OrderDetailBean bean = new MaterialPostBean.OrderDetailBean();
                    bean.setID(ID);
                    bean.setActualNumber(0);
                    bean.setUnit(shopCats.get(i).getUnit());
                    bean.setMaterialID(Integer.valueOf(shopCats.get(i).getMaterialid()));
                    bean.setName(shopCats.get(i).getName());
                    bean.setPrice(shopCats.get(i).getPrice());
                    bean.setNumber(shopCats.get(i).getCount());
                    list.add(bean);
                }
                materialPost.setOrderDetail(list);
                final String s = new Gson().toJson(materialPost);
                loadView.setVisibility(View.VISIBLE);
                rlSure.setEnabled(false);
                OkHttpUtils.postString()
                        .url(Constants.BASE_URL + "Order")
                        .content(s)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(OrderInfoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        loadView.setVisibility(View.GONE);
                        Toast.makeText(OrderInfoActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                        rlSure.setEnabled(true);
                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        CreatOrder creatOrder = gson.fromJson(response, CreatOrder.class);
                        if (creatOrder.getResult() == 0) {
                            loadView.setVisibility(View.GONE);
                            rlSure.setEnabled(true);
                            manager.deleteAll(PrefUtils.getString(UIUtils.getContext(), "projectId", ""));
                            Toast.makeText(OrderInfoActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                            ChangeTab event = new ChangeTab();
                            event.setPosition(0);
                            EventBus.getDefault().post(event);
                            Intent intent = new Intent(OrderInfoActivity.this, MentionMaterialActivity.class);
                            startActivity(intent);

                        }
                    }
                });
            }
        });


    }


    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
