package com.myygjc.ant.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.CaptchaInfoBean;
import com.myygjc.ant.project.doman.MessageEvent;
import com.myygjc.ant.project.procotol.CaptchaProcotol;
import com.myygjc.ant.project.procotol.LoginCatchaProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.MD5Utils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/1/4.
 * 登陆界面
 */

public class LoginActivity extends Activity {


    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_yzm)
    EditText etYzm;
    @Bind(R.id.ib_sendyzm)
    ImageButton ibSendyzm;
    @Bind(R.id.tv_send)
    TextView tvSend;
    @Bind(R.id.login)
    RelativeLayout login;
    @Bind(R.id.free_name)
    RelativeLayout freeName;
    @Bind(R.id.back)
    ImageButton back;

    String telRegex="[1][3578]\\d{9}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
        freeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        ibSendyzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = etPhone.getText().toString();

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(UIUtils.getContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    boolean matches = phone.matches(telRegex);
                    if(!matches){
                        Toast.makeText(UIUtils.getContext(),"请正确填写手机号",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CaptchaInfoBean infoBean = CaptchaProcotol.getCaptcha(phone, 2);
                        int result = infoBean.getResult();
                        final String message = infoBean.getMessage();

                        if (result == 1) {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        } else if(result==0) {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();

                ibSendyzm.setVisibility(View.GONE);
                tvSend.setVisibility(View.VISIBLE);
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageAtTime(1,1000);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = etPhone.getText().toString();
                String etyzm = etYzm.getText().toString();

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(UIUtils.getContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
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

                final String yzm = MD5Utils.md5(phone+etyzm);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CaptchaInfoBean captcha = LoginCatchaProcotol.getCaptcha(phone + yzm);
                        int result = captcha.getResult();
                        System.out.println(result);
                        final String message = captcha.getMessage();
                        if(result==1){
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }else if(result==2){
                    //TODO 网络请求获取客户核算id
                            System.out.println(message);
                                if (message.equals(DesUtils.encryptDes("0"))) {
                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UIUtils.getContext(), "我们正在审核你成为签约用户...,请耐心等待", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    MyApplication.getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UIUtils.getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                                            PrefUtils.putString(UIUtils.getContext(), "userId", message);

                                            PrefUtils.putString(UIUtils.getContext(), "user", phone);
                                            PrefUtils.putBoolean(UIUtils.getContext(), "islogin", true);

                                            MessageEvent messageEvent=new MessageEvent();
                                            EventBus.getDefault().post(messageEvent);
                                            finish();
                                        }
                                    });
                                }
                        }
                    }
                }).start();
            }
        });
    }


    private int i = 60;
    /**
     * 发送验证码的逻辑
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(i>=2){
                i--;
                tvSend.setText(i + "s");
            }else{
                i=60;
                tvSend.setVisibility(View.GONE);
                ibSendyzm.setVisibility(View.VISIBLE);
            }

            //每隔一秒发送一个消息
            handler.sendEmptyMessageDelayed(1, 1000);
        }

    };

    @Override
    public void onBackPressed() {
        finish();
        if(handler!=null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
