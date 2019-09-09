package com.mvc.dresssup.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.mvc.dresssup.R;
import com.mvc.dresssup.domain.PayComplete;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayOrderActivity extends Activity {


    @BindView(R.id.back)
    ImageButton back;
    private AlertView alertView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.bind(this);
        alertView = new AlertView("取消支付", "", null, new String[]{"取消", "放弃"}, null, PayOrderActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    return;
                } else {
                    EventBus.getDefault().post(new PayComplete());
                    finish();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelPayment();
            }
        });
    }

    private void cancelPayment() {
        //创建是否取消支付对话框
        if (!alertView.isShowing()) {
            alertView.show();
        }

    }

    @Override
    public void onBackPressed() {
        cancelPayment();
    }
}
