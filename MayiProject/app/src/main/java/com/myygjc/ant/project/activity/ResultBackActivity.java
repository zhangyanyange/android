package com.myygjc.ant.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.ResultMessage;
import com.myygjc.ant.project.doman.UserId;
import com.myygjc.ant.project.procotol.PayResultProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultBackActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_back);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String resultStatus = intent.getStringExtra("resultStatus");
        System.out.println("输出"+resultStatus);
        final String orderInfo = intent.getStringExtra("orderInfo");
        if(resultStatus!=null) {
            if (TextUtils.equals(resultStatus, "9000")) {
                new Thread() {
                    @Override
                    public void run() {
                        UserId money = PayResultProcotol.getResult(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")), orderInfo);
                        if (money.getResult() == 0) {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    zhifuweizi.setText("支付成功");
                                    orderNumber.setText(orderInfo);
                                    totalMoney.setText(PrefUtils.getString(UIUtils.getContext(), "total", ""));
                                }
                            });
                        }
                    }
                }.start();

            } else {
                zhifuweizi.setText("支付失败");
                llOrdernum.setVisibility(View.GONE);
            }
        }else{
            zhifuweizi.setText("支付成功");
            orderNumber.setText(orderInfo);
            totalMoney.setText(PrefUtils.getString(UIUtils.getContext(), "total", ""));
            zhifutu.setBackgroundResource(R.drawable.ic_iconmayi);
        }

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
    public void onBackPressed() {
        ResultMessage message = new ResultMessage();
        EventBus.getDefault().post(message);
        finish();

}
        }