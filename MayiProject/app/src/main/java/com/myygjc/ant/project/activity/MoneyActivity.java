package com.myygjc.ant.project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myygjc.ant.project.R;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoneyActivity extends Activity {

    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.my_money)
    TextView myMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        ButterKnife.bind(this);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        myMoney.setText(format.format(0));
    }
}
