package com.myygjc.ant.project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.MoneyHas;
import com.myygjc.ant.project.procotol.MoneyHasProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.UIUtils;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyMoneyActivity extends Activity {

    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.my_money)
    TextView myMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        final MoneyHasProcotol procotol=new MoneyHasProcotol();
        ButterKnife.bind(this);

        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new Thread(){
                @Override
                public void run() {
                    super.run();
                    final MoneyHas moneyHas = procotol.getMoney(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                    final NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
                    // 把转换后的货币String类型返回  
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            myMoney.setText(format.format(moneyHas.getContentResult()));
                        }
                    });

                }
            }.start();
    }
}
