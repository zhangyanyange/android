package com.myygjc.ant.project.activity;

import android.app.Activity;
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
import com.myygjc.ant.project.doman.CatchaVerity;
import com.myygjc.ant.project.procotol.CaptchaProcotol;
import com.myygjc.ant.project.procotol.VerityCaptchaProcotol;
import com.myygjc.ant.project.util.MD5Utils;
import com.myygjc.ant.project.util.UIUtils;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by zy2 on 2017/1/18.
 */

public class RegisterActivity extends Activity {

    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_yzm)
    EditText etYzm;
    @Bind(R.id.ib_sendyzm)
    ImageButton ibSendyzm;
    @Bind(R.id.login)
    RelativeLayout login;
    @Bind(R.id.tv_send)
    TextView tvSend;
    @Bind(R.id.back)
    ImageButton back;
    private String yzm;
    private String message;


    String telRegex="[1][3578]\\d{9}";
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ibSendyzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = etPhone.getText().toString();
                /**
                 * 验证手机号码是否为空
                 */
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
                        CaptchaInfoBean infoBean = CaptchaProcotol.getCaptcha(phone, 1);
                       int result = infoBean.getResult();
                        message = infoBean.getMessage();

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
                    phone = etPhone.getText().toString();
                    String etyzm = etYzm.getText().toString();
                    final String username = etUsername.getText().toString();
                    if (TextUtils.isEmpty(username)) {
                        Toast.makeText(UIUtils.getContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
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
                    yzm = MD5Utils.md5(phone + etyzm);
                    final CatchaVerity catcha = new CatchaVerity();
                    catcha.setName(username);
                    catcha.setCapchaCode(phone + yzm);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CaptchaInfoBean captchaInfoBean = VerityCaptchaProcotol.verityCaptcha(catcha);
                            int result = captchaInfoBean.getResult();
                            final String message = captchaInfoBean.getMessage();
                            if (result == 3) {
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setAlias();
                                        Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } else{
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(UIUtils.getContext(), message, Toast.LENGTH_SHORT).show();
                                    }
                                });
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

    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias() {
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, phone));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:

            }
        }
    };
}
