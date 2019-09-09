package com.microfeel.meiquetiliao.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.MainActivity;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.domain.CaptchaInfoBean;
import com.microfeel.meiquetiliao.domain.GetPeopleName;
import com.microfeel.meiquetiliao.domain.Token;
import com.microfeel.meiquetiliao.util.MD5Utils;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

public class LoginActivity extends Activity {
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_yzm)
    EditText etYzm;
    @Bind(R.id.bt_yzm)
    Button btYzm;
    @Bind(R.id.login)
    RelativeLayout login;

    String telRegex = "[1][3578]\\d{9}";


    private CountDownTimer timer;
    private String phone;


    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        EventBus.getDefault().register(this);
        ButterKnife.bind(this);

        if (PrefUtils.getBoolean(UIUtils.getContext(), "isLogin", false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }
        gson = new Gson();

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (btYzm.isEnabled()) {
                    if (charSequence.toString().length() == 11) {
                        btYzm.setBackgroundColor(Color.parseColor("#FF4500"));
                    } else {
                        btYzm.setBackgroundColor(Color.parseColor("#bdbdbd"));
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etYzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 4) {
                    login.setBackgroundColor(Color.parseColor("#FF4500"));
                } else {
                    login.setBackgroundColor(Color.parseColor("#bdbdbd"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = etPhone.getText().toString();

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(UIUtils.getContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean matches = phone.matches(telRegex);
                    if (!matches) {
                        Toast.makeText(UIUtils.getContext(), "请正确填写手机号", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    btYzm.setBackgroundColor(Color.parseColor("#bdbdbd"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            OkHttpUtils.get().url(Constants.BASE_URL + "Account/phoneCaptcha/" + phone).build().execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                    System.out.println(e.toString());
                                    Toast.makeText(LoginActivity.this, e.toString() + "验证码发送失败", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onResponse(String response) {
                                    Gson gson = new Gson();
                                    CaptchaInfoBean data = gson.fromJson(response, CaptchaInfoBean.class);
                                    Toast.makeText(LoginActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }).start();
                    btYzm.setEnabled(false);

                    timer = new CountDownTimer(60000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            btYzm.setText("" + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            btYzm.setText("重新发送");
                            btYzm.setBackgroundColor(Color.parseColor("#FF4500"));
                            btYzm.setEnabled(true);
                        }
                    }.start();

                }
            }
        });

        //登陆
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etyzm = etYzm.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(UIUtils.getContext(), "手机号不能为空或者请点击获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean matches = phone.matches(telRegex);
                    if (!matches) {
                        Toast.makeText(UIUtils.getContext(), "请正确填写手机号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (TextUtils.isEmpty(etyzm)) {
                    Toast.makeText(UIUtils.getContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String yzm = phone + etyzm;
                for (int i = 0; i < 5; i++) {
                    yzm = MD5Utils.md5(yzm);
                }
                String s = new Gson().toJson(phone + yzm);
                login.setEnabled(false);
                //对比验证码
                OkHttpUtils
                        .postString()
                        .url(Constants.BASE_URL + "Account/verityCaptcha")
                        .content(s)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                login.setEnabled(true);
                            }

                            @Override
                            public void onResponse(String response) {

                                CaptchaInfoBean data = gson.fromJson(response, CaptchaInfoBean.class);
                                Toast.makeText(LoginActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();

                                if(data.getResult()!=0){
                                    btYzm.setEnabled(true);
                                    login.setEnabled(true);
                                    return;
                                }

//                                OkHttpUtils.get().url(Constants.BASE_URL + "Account/loginPhoenix?id=" + data.getStringContent()).build().execute(new StringCallback() {
//                                    @Override
//                                    public void onError(Call call, Exception e) {
//                                        System.out.println("错误"+e.toString());
//                                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                                        login.setEnabled(true);
//                                    }
//
//                                    @Override
//                                    public void onResponse(String response) {
//
//
//                                        OkHttpUtils.get().url(Constants.BASE_URL + "Account/UserInfo").build().execute(new StringCallback() {
//                                            @Override
//                                            public void onError(Call call, Exception e) {
//                                                login.setEnabled(true);
//                                            }
//
//                                            @Override
//                                            public void onResponse(String response) {
//                                                GetPeopleName name = gson.fromJson(response, GetPeopleName.class);
//                                                EventBus.getDefault().post(new FinisnMain());
//                                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
//                                                login.setEnabled(true);
//                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                                PrefUtils.putBoolean(UIUtils.getContext(), "isLogin", true);
//                                                PrefUtils.putString(UIUtils.getContext(), "tv_phone", phone);
//                                                PrefUtils.putString(UIUtils.getContext(), "tv_name", name.getEmail());
//                                                finish();
//                                            }
//                                        });
//                                    }
//                                });

                                Map<String,String> map=new HashMap<>();
                                map.put("grant_type","password");
                                map.put("username",data.getStringContent());

                                OkHttpUtils
                                        .post()
                                        .url("https://meiqueapi.microfeel.net:12301/MQTLApi/token")
                                        .params(map)
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e) {

                                            }

                                            @Override
                                            public void onResponse(String response) {
                                                Token token = gson.fromJson(response, Token.class);
                                                PrefUtils.putString(UIUtils.getContext(),"token",token.getRefresh_token()); //记录refresh_token
                                                PrefUtils.putString(UIUtils.getContext(),"assets_token",token.getAccess_token());//记录assets_token


                                                OkHttpUtils.get().url(Constants.BASE_URL + "Account/UserInfo").build().execute( new StringCallback() {
                                                    @Override
                                                    public void onError(Call call, Exception e) {
                                                        login.setEnabled(true);
                                                    }
                                                    @Override
                                                    public void onResponse(String response) {
                                                        GetPeopleName name = gson.fromJson(response, GetPeopleName.class);

                                                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                                        login.setEnabled(true);
                                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                        finish();
                                                        PrefUtils.putInt(UIUtils.getContext(),"refresh_token_code",2);
                                                        PrefUtils.putBoolean(UIUtils.getContext(), "isLogin", true);
                                                        PrefUtils.putString(UIUtils.getContext(), "tv_phone", phone);
                                                        PrefUtils.putString(UIUtils.getContext(), "tv_name", name.getEmail());

                                                    }
                                                });
                                            }
                                        });
                            }
                        });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        btYzm.setEnabled(true);
        login.setEnabled(true);
//        if(!PrefUtils.getBoolean(UIUtils.getContext(), "isLogin", false)){
//            EventBus.getDefault().post(new FinisnMain());
//        }
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void payComplete(FinisnLogin payComplete) {
//        finish();
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        EventBus.getDefault().post(new FinisnMain());
//    }
}
