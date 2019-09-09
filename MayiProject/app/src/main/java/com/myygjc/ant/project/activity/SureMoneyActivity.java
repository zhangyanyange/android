package com.myygjc.ant.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.constants.Constants;
import com.myygjc.ant.project.doman.MoneyHas;
import com.myygjc.ant.project.doman.PayOrder;
import com.myygjc.ant.project.doman.PayResult;
import com.myygjc.ant.project.doman.ResultMessage;
import com.myygjc.ant.project.doman.WxOrderInfo;
import com.myygjc.ant.project.procotol.MoneyHasProcotol;
import com.myygjc.ant.project.procotol.PayOrderProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.OrderInfoUtil2_0;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SureMoneyActivity extends Activity {

    @Bind(R.id.back)
    ImageButton back;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.hor_money)
    TextView horMoney;
    @Bind(R.id.cb_yue)
    CheckBox cbYue;
    @Bind(R.id.cb_weixin)
    CheckBox cbWeixin;
    @Bind(R.id.cb_zhifubao)
    CheckBox cbZhifubao;
    @Bind(R.id.sure_money)
    RelativeLayout sureMoney;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.ll_net_request)
    LinearLayout llNetRequest;


    private String orderInfo;

    private int zffs = 0;
    private WxOrderInfo data;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();

                    Intent intent = new Intent(SureMoneyActivity.this, ResultBackActivity.class);
                    intent.putExtra("resultStatus", resultStatus);
                    intent.putExtra("orderInfo", orderInfo);
                    startActivity(intent);

            }
        }
    };
    private MoneyHas userId;
    private NiftyDialogBuilder dialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_sure_money);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final MoneyHasProcotol moneyHasProcotol = new MoneyHasProcotol();
        final IWXAPI api = WXAPIFactory.createWXAPI(UIUtils.getContext(), null);
        api.registerApp(Constants.APP_ID);
        orderInfo = intent.getStringExtra("orderInfo");
        final double henmoney = intent.getDoubleExtra("henmoney", 0);
        final double totalmoney = intent.getDoubleExtra("total", 0);
        PrefUtils.putString(UIUtils.getContext(), "orderInfo", orderInfo);
        dialogBuilder = NiftyDialogBuilder.getInstance(SureMoneyActivity.this);
        orderNumber.setText(orderInfo);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // 把转换后的货币String类型返回  
        String heng = format.format(henmoney);
        final String total = format.format(henmoney + totalmoney);
        PrefUtils.putString(UIUtils.getContext(), "total", total);
        horMoney.setText(heng);
        final PayOrderProcotol procotol = new PayOrderProcotol();
        tvTotal.setText("确认支付(" + total + ")");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultMessage message = new ResultMessage();
                EventBus.getDefault().post(message);
                finish();
            }
        });


        cbYue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llNetRequest.setVisibility(View.VISIBLE);
                    cbWeixin.setChecked(false);
                    cbZhifubao.setChecked(false);
                    cbWeixin.setEnabled(false);
                    cbZhifubao.setChecked(false);
                    sureMoney.setEnabled(false);
                    new Thread(){
                        @Override
                        public void run() {
                            userId = moneyHasProcotol.getMoney(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                            if(userId.getContentResult()>=(henmoney + totalmoney)){
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        llNetRequest.setVisibility(View.GONE);
                                        cbWeixin.setEnabled(true);
                                        cbZhifubao.setEnabled(true);
                                        sureMoney.setEnabled(true);
                                        zffs = 1;
                                    }
                                });
                            }else{
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        llNetRequest.setVisibility(View.GONE);
                                        cbWeixin.setEnabled(true);
                                        cbZhifubao.setEnabled(true);
                                        sureMoney.setEnabled(true);
                                        cbYue.setChecked(false);
                                        Toast.makeText(UIUtils.getContext(),"额度不足，请选择其他方式支付",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }.start();
                } else {
                    zffs = 0;
                }
            }
        });
        cbWeixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llNetRequest.setVisibility(View.VISIBLE);
                    cbYue.setChecked(false);
                    cbZhifubao.setChecked(false);
                    cbYue.setEnabled(false);
                    cbZhifubao.setChecked(false);
                    sureMoney.setEnabled(false);
                    zffs = 2;
                    new Thread() {
                        @Override
                        public void run() {
                            PayOrder payOrder = new PayOrder();
                            payOrder.setTldh(orderInfo);
                            payOrder.setZffs(zffs);
                            data = procotol.getOrderInfo(payOrder);
                        }
                    }.start();
                } else {
                    zffs = 0;
                }
            }
        });
        cbZhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llNetRequest.setVisibility(View.VISIBLE);
                    cbWeixin.setChecked(false);
                    cbYue.setChecked(false);
                    cbWeixin.setEnabled(false);
                    cbYue.setEnabled(false);
                    sureMoney.setEnabled(false);
                    zffs = 3;
                    new Thread() {
                        @Override
                        public void run() {
                            PayOrder payOrder = new PayOrder();
                            payOrder.setTldh(orderInfo);
                            payOrder.setZffs(zffs);
                            data = procotol.getOrderInfo(payOrder);
                        }
                    }.start();
                } else {
                    zffs = 0;
                }
            }
        });

        sureMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureMoney.setEnabled(false);
                if (zffs == 0) {
                    Toast.makeText(UIUtils.getContext(), "请选择支付方式", Toast.LENGTH_SHORT).show();
                } else if (zffs == 1) {
                    dialogBuilder
                            .withTitle("满1000免运费")
                            .withTitleColor("#FFFFFF")
                            .withDividerColor("#11000000")
                            .withMessage("合计:"+total)
                            .withMessageColor("#FFFFFFFF")
                            .withDialogColor("#FFE74C3C")
                            .withIcon(getResources().getDrawable(R.drawable.ic_iconmayi))
                            .withDuration(700)
                            .withButton1Text("确认支付")
                            .withButton2Text("取消支付")
                            .isCancelableOnTouchOutside(true)
                            .setButton1Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogBuilder.cancel();
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            PayOrder payOrder = new PayOrder();
                                            payOrder.setTldh(orderInfo);
                                            payOrder.setZffs(zffs);
                                            WxOrderInfo wxorderInfo = procotol.getOrderInfo(payOrder);
                                            if(wxorderInfo.getState()==1){
                                                Intent intent = new Intent(SureMoneyActivity.this, ResultBackActivity.class);
                                                intent.putExtra("orderInfo", orderInfo);
                                                startActivity(intent);
                                                sureMoney.setEnabled(true);

                                            }else{
                                                MyApplication.getHandler().post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(UIUtils.getContext(),"支付失败",Toast.LENGTH_SHORT).show();
                                                        sureMoney.setEnabled(true);
                                                    }
                                                });
                                            }
                                        }
                                    }.start();
                                }
                            })
                            .setButton2Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogBuilder.cancel();
                                    sureMoney.setEnabled(true);
                                }
                            })
                            .show();

                    sureMoney.setEnabled(true);

                } else if (zffs == 2) {
                    dialogBuilder
                            .withTitle("满1000免运费")
                            .withTitleColor("#FFFFFF")
                            .withDividerColor("#11000000")
                            .withMessage("合计:"+total)
                            .withMessageColor("#FFFFFFFF")
                            .withDialogColor("#FFE74C3C")
                            .withIcon(getResources().getDrawable(R.drawable.lvselogo))
                            .withDuration(700)
                            .withButton1Text("确认支付")
                            .withButton2Text("取消支付")
                            .isCancelableOnTouchOutside(true)
                            .setButton1Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogBuilder.cancel();
                                    PayReq req = new PayReq();
                                    req.appId = Constants.APP_ID;
                                    req.partnerId = Constants.PARTNER_ID;
                                    req.prepayId = data.getYddh();
                                    req.nonceStr = data.getSjzfc();
                                    req.timeStamp = data.getSjc();
                                    req.packageValue = "Sign=WXPay";
                                    req.sign = data.getQm();
                                    api.sendReq(req);
                                    sureMoney.setEnabled(true);
                                }
                            })
                            .setButton2Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogBuilder.cancel();
                                    sureMoney.setEnabled(true);
                                }
                            })
                            .show();

                    sureMoney.setEnabled(true);

                } else if (zffs == 3) {

                    dialogBuilder
                            .withTitle("满1000免运费")
                            .withTitleColor("#FFFFFF")
                            .withDividerColor("#11000000")
                            .withMessage("合计:"+total)
                            .withMessageColor("#FFFFFFFF")
                            .withDialogColor("#FFE74C3C")
                            .withIcon(getResources().getDrawable(R.drawable.zhifubaologo))
                            .withDuration(700)
                            .withButton1Text("确认支付")
                            .withButton2Text("取消支付")
                            .isCancelableOnTouchOutside(true)
                            .setButton1Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogBuilder.cancel();
                                    new Thread() {
                                        @Override
                                        public void run() {

                                            DecimalFormat format1 = new DecimalFormat(".00");
                                            String format2 = format1.format(henmoney + totalmoney);
                                            Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap("2017022105793231", format2, true, orderInfo, data.getSjc());

                                            String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

                                            String encode = "";

                                            try {
                                                encode = "sign=" + URLEncoder.encode(data.getQm(), "UTF-8");
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }

                                            String info = orderParam + "&" + encode;
                                            PayTask payTask = new PayTask(SureMoneyActivity.this);
                                            Map<String, String> result = payTask.payV2(info, true);

                                            Message msg = new Message();
                                            msg.what = 1;
                                            msg.obj = result;
                                            mHandler.sendMessage(msg);
                                            sureMoney.setEnabled(true);
                                        }
                                    }.start();
                                }
                            })
                            .setButton2Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogBuilder.cancel();
                                    sureMoney.setEnabled(true);
                                }
                            })
                            .show();

                    sureMoney.setEnabled(true);
                }
            }
        });

        procotol.setOnSuccess(new PayOrderProcotol.OnNetFinish() {
            @Override
            public void onSuccessListener() {
                llNetRequest.setVisibility(View.GONE);
                cbWeixin.setEnabled(true);
                cbYue.setEnabled(true);
                cbZhifubao.setEnabled(true);
                sureMoney.setEnabled(true);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(ResultMessage messageEvent) {
        finish();
    }

    @Override
    public void onBackPressed() {
        ResultMessage message = new ResultMessage();
        EventBus.getDefault().post(message);
        finish();
    }
}
