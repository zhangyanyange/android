package com.myygjc.ant.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.LocationActivity;
import com.myygjc.ant.project.activity.SureMoneyActivity;
import com.myygjc.ant.project.base.BaseFragment;
import com.myygjc.ant.project.base.LoadingPager;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.db.LocationManager;
import com.myygjc.ant.project.doman.GetCarBean;
import com.myygjc.ant.project.doman.LocationInfo;
import com.myygjc.ant.project.doman.MaterialPost;
import com.myygjc.ant.project.doman.ShopOrderInfo;
import com.myygjc.ant.project.procotol.GetCarProcotol;
import com.myygjc.ant.project.procotol.PayOrderProcotol;
import com.myygjc.ant.project.procotol.PostMaterialProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/2/22.
 */

public class AddOrderFragment extends BaseFragment {
    @Bind(R.id.iv_arrow)
    ImageView ivArrow;

    @Bind(R.id.et_remove)
    EditText etRemove;
    @Bind(R.id.et_sendtime)
    TextView etSendtime;
    @Bind(R.id.ll_sendtime)
    LinearLayout llSendtime;
    @Bind(R.id.cb_self)
    CheckBox cbSelf;
    @Bind(R.id.et_seconddistance)
    EditText etSeconddistance;
    @Bind(R.id.ll_distance)
    LinearLayout llDistance;
    @Bind(R.id.et_countstair)
    EditText etCountstair;
    @Bind(R.id.ll_stair)
    LinearLayout llStair;
    @Bind(R.id.rl_sure)
    RelativeLayout rlSure;
    @Bind(R.id.activity_userdetail)
    LinearLayout activityUserdetail;
    TimePickerView pvTime;
    @Bind(R.id.rb_count1)
    CheckBox rbCount1;
    @Bind(R.id.rb_count2)
    CheckBox rbCount2;
    @Bind(R.id.ll_net_request)
    LinearLayout llNetRequest;
    @Bind(R.id.cb_sfby)
    CheckBox cbSfby;
    @Bind(R.id.ll_sfby)
    LinearLayout llSfby;
    @Bind(R.id.et_name)
    TextView etName;
    @Bind(R.id.et_phone)
    TextView etPhone;
    @Bind(R.id.et_sendlocation)
    TextView etSendlocation;
    @Bind(R.id.ll_location)
    LinearLayout llLocation;
    private ArrayList<GetCarBean> data;
    private ArrayList<Integer> choice;
    private double total;
    String telRegex = "[1][3578]\\d{9}";
    private PostMaterialProcotol procotol;
    private ShopOrderInfo shopOrderInfo;
    private LocationManager manager;
    private boolean isHas = false;
    private LocationInfo locationInfo;

    @Override
    public LoadingPager.LoadedResult initData() {
        Bundle bundle = getArguments();
        choice = bundle.getIntegerArrayList("choice");
        data = bundle.getParcelableArrayList("data");
        GetCarProcotol getCarProcotol = new GetCarProcotol();

        procotol = new PostMaterialProcotol();
        return checkResult(this.data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 3:
                ArrayList<LocationInfo> query = manager.query();
                for (int i = 0; i < query.size(); i++) {
                    System.out.println(PrefUtils.getInt(UIUtils.getContext(), "zj", -3));
                    if (PrefUtils.getInt(UIUtils.getContext(), "zj", -3) == query.get(i).getId()) {
                        isHas = true;
                        locationInfo = query.get(i);
                        break;
                    }
                }
                if (!isHas) {
                    Toast.makeText(UIUtils.getContext(), "必须填写默认地址", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UIUtils.getContext(), LocationActivity.class);
                    startActivityForResult(intent, 3);
                }
                if (locationInfo != null) {
                    TextPaint tp = etName.getPaint();
                    tp.setFakeBoldText(true);
                    TextPaint paint = etPhone.getPaint();
                    paint.setFakeBoldText(true);
                    etName.setText(locationInfo.getName());
                    etPhone.setText(locationInfo.getTellphone());
                    etSendlocation.setText(locationInfo.getLocation());
                }
                break;
        }

    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_userdetail, null);
        ButterKnife.bind(this, view);

        llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHas=false;
                Intent intent = new Intent(UIUtils.getContext(), LocationActivity.class);
                startActivityForResult(intent, 3);
            }
        });
        manager = new LocationManager(UIUtils.getContext());
        ArrayList<LocationInfo> query = manager.query();
        for (int i = 0; i < query.size(); i++) {
            if (PrefUtils.getInt(UIUtils.getContext(), "zj", -3) == query.get(i).getId()) {
                isHas = true;
                locationInfo = query.get(i);
                break;
            }
        }
        if (!isHas) {
            Intent intent = new Intent(UIUtils.getContext(), LocationActivity.class);
            startActivityForResult(intent, 3);
        }
        if (locationInfo != null) {
            TextPaint tp = etName.getPaint();
            tp.setFakeBoldText(true);
            TextPaint paint = etPhone.getPaint();
            paint.setFakeBoldText(true);
            etName.setText(locationInfo.getName());
            etPhone.setText(locationInfo.getTellphone());
            etSendlocation.setText(locationInfo.getLocation());
        }
        final MaterialPost materialPost = new MaterialPost();
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        llSendtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
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
                        Date date1 = new Date();
                        String time1 = getTime(date);
                        String time2 = getTime(date1);
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
//                String name = etName.getText().toString();
//                String phone = etPhone.getText().toString();
                String remark = etRemove.getText().toString();
//                String sendLocation = etSendlocation.getText().toString();
                String sendTime = etSendtime.getText().toString();

//                if (TextUtils.isEmpty(name)) {
//                    Toast.makeText(UIUtils.getContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(phone)) {
//                    Toast.makeText(UIUtils.getContext(), "电话号码不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    boolean matches = phone.matches(telRegex);
//                    if (!matches) {
//                        Toast.makeText(UIUtils.getContext(), "请正确填写手机号", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//
//
//                if (TextUtils.isEmpty(sendLocation)) {
//                    Toast.makeText(UIUtils.getContext(), "送货地址不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(sendTime)) {
//                    Toast.makeText(UIUtils.getContext(), "想要送达不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                materialPost.setLxr(etName.getText().toString());
                materialPost.setLxdh(etPhone.getText().toString());
                materialPost.setBz(remark);
                materialPost.setShdz(etSendlocation.getText().toString());
                materialPost.setJhjcsj(sendTime);
                materialPost.setSfxyby(cbSfby.isChecked());
                materialPost.setKhhsid(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                ArrayList<MaterialPost.TldmxBean> list = new ArrayList<MaterialPost.TldmxBean>();
                total = 0;
                for (int i = 0; i < choice.size(); i++) {
                    MaterialPost.TldmxBean tl = new MaterialPost.TldmxBean();
                    tl.setID(Integer.parseInt(data.get(choice.get(i)).getWlId()));
                    double dj = data.get(choice.get(i)).getDj();
                    tl.setPrice(dj);
                    tl.setQty(data.get(choice.get(i)).getSl());
                    total += dj * data.get(choice.get(i)).getSl();
                    list.add(tl);
                }
                materialPost.setTldmx(list);


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
                    materialPost.setEcdyjl(Integer.parseInt(seconddistance));
                    materialPost.setDtlcs(Integer.parseInt(CountTair));
                    materialPost.setLtcs(0);

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
                    materialPost.setEcdyjl(Integer.parseInt(seconddistance));
                    materialPost.setLtcs(Integer.parseInt(CountTair));
                    materialPost.setDtlcs(0);
                }
                if (!cbSelf.isChecked()) {
                    materialPost.setSfzq(false);
                    if (cbSfby.isChecked()) {
                        if (!rbCount1.isChecked() && !rbCount2.isChecked()) {
                            Toast.makeText(UIUtils.getContext(), "请选择楼梯还是电梯", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        materialPost.setSfzq(true);
                        materialPost.setSfxyby(false);
                        materialPost.setEcdyjl(0);
                        materialPost.setDtlcs(0);
                        materialPost.setLtcs(0);
                    }
                } else {
                    materialPost.setSfzq(true);
                    materialPost.setSfxyby(false);
                    materialPost.setEcdyjl(0);
                    materialPost.setDtlcs(0);
                    materialPost.setLtcs(0);
                }
                new Thread() {
                    @Override
                    public void run() {
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                llNetRequest.setVisibility(View.VISIBLE);
                                rlSure.setEnabled(false);
                            }
                        });
                        shopOrderInfo = procotol.verityCaptcha(materialPost);

                        if (shopOrderInfo.getState() == 1) {
                            Intent intent = new Intent(UIUtils.getContext(), SureMoneyActivity.class);
                            intent.putExtra("orderInfo", shopOrderInfo.getDh());
                            intent.putExtra("henmoney", shopOrderInfo.getSpyf() + shopOrderInfo.getCzyf() + shopOrderInfo.getDyyf());
                            intent.putExtra("total", total);
                            //TODO 进行订单提交
                            startActivity(intent);
                        } else {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UIUtils.getContext(), "订单创建失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }.start();
            }
        });

        procotol.setOnSuccess(new PayOrderProcotol.OnNetFinish() {
            @Override
            public void onSuccessListener() {
                llNetRequest.setVisibility(View.GONE);
                rlSure.setEnabled(true);

            }
        });

        return view;

    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}

