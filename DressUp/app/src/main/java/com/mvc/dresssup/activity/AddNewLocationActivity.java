package com.mvc.dresssup.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mvc.dresssup.R;
import com.mvc.dresssup.base.MyApplication;
import com.mvc.dresssup.domain.AddressBean;
import com.mvc.dresssup.domain.AddressStateBean;
import com.mvc.dresssup.procotol.AddAddressProcotol;
import com.mvc.dresssup.procotol.ChangeAddressProcotol;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewLocationActivity extends Activity {


    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.complete)
    TextView complete;
    @BindView(R.id.rl_fukuan)
    RelativeLayout rlFukuan;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_location)
    EditText tvLocation;
    @BindView(R.id.loadView)
    ProgressBar loadView;
    @BindView(R.id.activity_add_new_location)
    LinearLayout activityAddNewLocation;
    private AddressBean bean;
    private AddressBean data;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        bean = new AddressBean();
        Intent intent = getIntent();
        data = intent.getParcelableExtra("location");
        flag = intent.getStringExtra("flag");
        if (data != null) {
            tvName.setText(data.getConsignee());
            tvPhone.setText(data.getTelephone());
            tvLocation.setText(data.getAddress());
            bean.setIsDefault(data.isIsDefault());
            bean.setId(data.getId());
        }
        tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = tvPhone.getText().toString();
                String location = tvLocation.getText().toString();

                if (charSequence.toString().length() != 0 && phone.length() != 0 && location.length() != 0) {
                    complete.setTextColor(Color.parseColor("#58B861"));
                    complete.setEnabled(true);
                } else {
                    complete.setTextColor(Color.parseColor("#bdbdbd"));
                    complete.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = tvName.getText().toString();
                String location = tvLocation.getText().toString();

                if (charSequence.toString().length() != 0 && name.length() != 0 && location.length() != 0) {
                    complete.setTextColor(Color.parseColor("#58B861"));
                    complete.setEnabled(true);
                } else {
                    complete.setTextColor(Color.parseColor("#bdbdbd"));
                    complete.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phone = tvPhone.getText().toString();
                String name = tvName.getText().toString();

                if (charSequence.toString().length() != 0 && phone.length() != 0 && name.length() != 0) {
                    complete.setTextColor(Color.parseColor("#58B861"));
                    complete.setEnabled(true);
                } else {
                    complete.setTextColor(Color.parseColor("#bdbdbd"));
                    complete.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complete.setEnabled(false);
                loadView.setVisibility(View.VISIBLE);
                if (flag != null && flag.equals("1")) {
                    bean.setIsDefault(1);
                }
                bean.setConsignee(tvName.getText().toString());
                bean.setAddress(tvLocation.getText().toString());
                bean.setTelephone(tvPhone.getText().toString());
                if (data == null) {
                    addNewLocation(bean);
                } else {
                    changeLocation(bean);
                }

            }
        });
    }

    public void addNewLocation(final AddressBean bean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AddressStateBean stateBean = AddAddressProcotol.addAddress(bean);
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadView.setVisibility(View.GONE);
                        complete.setEnabled(true);
                    }
                });
                if (stateBean != null) {
                    if (stateBean.getResult() == 1) {
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddNewLocationActivity.this, "新增地址成功", Toast.LENGTH_SHORT).show();
                                if (stateBean.getListContent() != null) {
                                    EventBus.getDefault().post(stateBean.getListContent());
                                    finish();
                                }
                            }
                        });
                    } else {
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddNewLocationActivity.this, "新增地址失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddNewLocationActivity.this, "新增地址失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void changeLocation(final AddressBean bean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AddressStateBean stateBean = ChangeAddressProcotol.changeAddress(bean);
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadView.setVisibility(View.GONE);
                        complete.setEnabled(true);
                    }
                });
                if (stateBean != null) {
                    if (stateBean.getResult() == 1) {
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddNewLocationActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(stateBean.getListContent());
                                finish();
                            }
                        });
                    } else {
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddNewLocationActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddNewLocationActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
