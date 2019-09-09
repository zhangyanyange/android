package com.mvc.dresssup.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mvc.dresssup.R;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.base.MyApplication;
import com.mvc.dresssup.domain.CaptchaDataBean;
import com.mvc.dresssup.domain.CaptchaInfoBean;
import com.mvc.dresssup.domain.LoginRefresh;
import com.mvc.dresssup.domain.Verity;
import com.mvc.dresssup.procotol.LoginProcotol;
import com.mvc.dresssup.procotol.VerityCaptchaProcotol;
import com.mvc.dresssup.util.MD5Utils;
import com.mvc.dresssup.util.PrefUtils;
import com.mvc.dresssup.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LoginActivity extends Activity {

    String telRegex = "[1][3578]\\d{9}";
    @BindView(R.id.loadView)
    ProgressBar loadView;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.rl_fukuan)
    RelativeLayout rlFukuan;
    @BindView(R.id.iv_login)
    ImageView ivLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_sendyzm)
    TextView etSendyzm;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.sanfang)
    TextView sanfang;
    @BindView(R.id.qq)
    ImageView qq;
    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;

    private CountDownTimer timer;
    private String phone;

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etSendyzm.isEnabled()) {
                    if (charSequence.toString().length() == 11) {
                        etSendyzm.setBackgroundColor(Color.parseColor("#58B861"));
                    } else {
                        etSendyzm.setBackgroundColor(Color.parseColor("#bdbdbd"));
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
                    login.setBackgroundColor(Color.parseColor("#58b861"));
                } else {
                    login.setBackgroundColor(Color.parseColor("#bdbdbd"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etSendyzm.setOnClickListener(new View.OnClickListener() {
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
                    loadView.setVisibility(View.VISIBLE);
                    etSendyzm.setBackgroundColor(Color.parseColor("#bdbdbd"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            OkHttpUtils.get().url(Constants.BASE_URL + "Account/phoneCaptcha/" + phone).build().execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                    Toast.makeText(LoginActivity.this, e.toString() + "验证码发送失败", Toast.LENGTH_LONG).show();
                                    loadView.setVisibility(View.GONE);
                                }

                                @Override
                                public void onResponse(String response) {
                                    Gson gson = new Gson();
                                    CaptchaInfoBean data = gson.fromJson(response, CaptchaInfoBean.class);
                                    Toast.makeText(LoginActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                                    loadView.setVisibility(View.GONE);
                                }
                            });


                        }
                    }).start();
                    etSendyzm.setEnabled(false);

                    timer = new CountDownTimer(60000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            etSendyzm.setText("" + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            etSendyzm.setText("重新发送");
                            etSendyzm.setBackgroundColor(Color.parseColor("#FF4500"));
                            etSendyzm.setEnabled(true);
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

                loadView.setVisibility(View.VISIBLE);

                final String yzm = MD5Utils.md5(phone + etyzm);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Verity verity = new Verity();
                        verity.setCode(phone + yzm);
                        CaptchaInfoBean captchaInfoBean = VerityCaptchaProcotol.verityCaptcha(verity);
                        if (captchaInfoBean != null) {
                            if (captchaInfoBean.getResult() == 0) {
                                final CaptchaDataBean bean = LoginProcotol.login(captchaInfoBean.getStringContent());
                                if (bean.getResult() == 0) {
                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            PrefUtils.putBoolean(UIUtils.getContext(), "isLogin", true);
                                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                            loadView.setVisibility(View.GONE);
                                            EventBus.getDefault().post(new LoginRefresh());
                                            finish();

                                        }
                                    });
                                } else {
                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            loginFailure();
                                        }
                                    });
                                }
                            } else {
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        loginFailure();
                                    }
                                });
                            }
                        } else {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    loginFailure();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

    }

    private void loginFailure() {
        PrefUtils.putBoolean(UIUtils.getContext(), "isLogin", false);
        Toast.makeText(LoginActivity.this, "登陆失败，请检查验证码", Toast.LENGTH_SHORT).show();
        loadView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
