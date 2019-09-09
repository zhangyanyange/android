package com.mvc.microfeel.activity;

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
import com.mvc.microfeel.MainActivity;
import com.mvc.microfeel.R;
import com.mvc.microfeel.base.MyApplication;
import com.mvc.microfeel.constans.Constants;
import com.mvc.microfeel.domain.CaptchaDataBean;
import com.mvc.microfeel.domain.CaptchaInfoBean;
import com.mvc.microfeel.protocol.LoginProcotol;
import com.mvc.microfeel.protocol.VerityCaptchaProcotol;
import com.mvc.microfeel.util.MD5Utils;
import com.mvc.microfeel.util.PrefUtils;
import com.mvc.microfeel.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.Call;

public class LoginActivity extends Activity {
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_yzm)
    EditText etYzm;
    @Bind(R.id.bt_yzm)
    Button btYzm;
    @Bind(R.id.login)
    RelativeLayout login;

    String telRegex="[1][3578]\\d{9}";


    private CountDownTimer timer;
    private String phone;

    private PromptDialog promptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        promptDialog = new PromptDialog(LoginActivity.this);

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(btYzm.isEnabled()){
                    if (charSequence.toString().length() == 11) {
                        btYzm.setBackgroundColor(Color.parseColor("#FF4500"));
                    }else{
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
                }else{
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
                }else{
                    boolean matches = phone.matches(telRegex);
                    if(!matches){
                        Toast.makeText(UIUtils.getContext(),"请正确填写手机号",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    btYzm.setBackgroundColor(Color.parseColor("#bdbdbd"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            OkHttpUtils.get().url(Constants.BASE_URL+"Account/phoneCaptcha/"+ phone).build().execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {

                                    Toast.makeText(LoginActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response) {
                                    Gson gson=new Gson();
                                    CaptchaInfoBean data=gson.fromJson(response,CaptchaInfoBean.class);
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
                }else{
                    boolean matches = phone.matches(telRegex);
                    if(!matches){
                        Toast.makeText(UIUtils.getContext(),"请正确填写手机号",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (TextUtils.isEmpty(etyzm)) {
                    Toast.makeText(UIUtils.getContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                promptDialog.showLoading("正在登录");

                final String yzm = MD5Utils.md5(phone+etyzm);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                              CaptchaInfoBean captchaInfoBean = VerityCaptchaProcotol.verityCaptcha(phone + yzm);
                                if(captchaInfoBean!=null){
                                    if(captchaInfoBean.getResult()==0){
                                        //dd0136eb-5319-4a38-9e89-c23763128b29$752597ec-34c5-4bf4-a334-958bc87b40bc
//                                    final CaptchaDataBean bean = LoginProcotol.login("dd0136eb-5319-4a38-9e89-c23763128b29$752597ec-34c5-4bf4-a334-958bc87b40bc");
                                        final CaptchaDataBean bean = LoginProcotol.login(captchaInfoBean.getStringContent());
                                        if(bean.getResult()==0){
                                            MyApplication.getHandler().post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    promptDialog.showSuccess("登陆成功");
                                                    promptDialog.dismiss();
                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                    PrefUtils.putBoolean(UIUtils.getContext(),"isLogin",true);
                                                    PrefUtils.putString(UIUtils.getContext(),"tv_name",bean.getEmployeeName());
                                                    PrefUtils.putString(UIUtils.getContext(),"tv_gongsi",bean.getOrgName());
                                                    PrefUtils.putString(UIUtils.getContext(),"tv_phone",phone);
                                                    finish();
                                                }
                                            });
                                        }else{
                                            MyApplication.getHandler().post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    promptDialog.showWarn("登陆失败，请检查验证码",false);
                                                    promptDialog.dismiss();
                                                }
                                            });
                                        }
                                    }else{
                                        MyApplication.getHandler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                promptDialog.showWarn("登陆失败，请检查验证码",false);
                                                promptDialog.dismiss();
                                            }
                                        });
                                    }
                                }else{
                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            promptDialog.showWarn("登陆失败，请检查验证码");
                                            promptDialog.dismiss();
                                        }
                                    });
                                }
                            }
                        }).start();


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }
    }
}
