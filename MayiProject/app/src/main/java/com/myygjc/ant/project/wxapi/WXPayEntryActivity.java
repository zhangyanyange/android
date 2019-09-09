package com.myygjc.ant.project.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.constants.Constants;
import com.myygjc.ant.project.doman.ResultMessage;
import com.myygjc.ant.project.doman.UserId;
import com.myygjc.ant.project.procotol.PayResultProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    @Bind(R.id.back)
    ImageButton back;
    @Bind(R.id.rl_fukuan)
    RelativeLayout rlFukuan;
    @Bind(R.id.zhifutu)
    ImageView zhifutu;
    @Bind(R.id.zhifuweizi)
    TextView zhifuweizi;
    @Bind(R.id.activity_result_back)
    RelativeLayout activityResultBack;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.total_money)
    TextView totalMoney;
    @Bind(R.id.ll_ordernum)
    LinearLayout llOrdernum;

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        ButterKnife.bind(this);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultMessage message = new ResultMessage();
                EventBus.getDefault().post(message);
                finish();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        System.out.println("错误码" + resp.errCode);

        if (resp.errCode == 0) {
            new Thread() {
                @Override
                public void run() {
                    UserId money = PayResultProcotol.getResult(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")), PrefUtils.getString(UIUtils.getContext(), "orderInfo", ""));
                    if (money.getResult() == 0) {
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                zhifuweizi.setText("支付成功");
                                orderNumber.setText(PrefUtils.getString(UIUtils.getContext(), "orderInfo", ""));
                                totalMoney.setText(PrefUtils.getString(UIUtils.getContext(), "total", ""));
                            }
                        });
                    }
                }
            }.start();
        } else {
            llOrdernum.setVisibility(View.GONE);
            zhifuweizi.setText("支付失败");
        }
    }

    @Override
    public void onBackPressed() {
        ResultMessage message = new ResultMessage();
        EventBus.getDefault().post(message);
        finish();
    }
}