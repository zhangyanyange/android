package com.myygjc.ant.project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.db.LocationManager;
import com.myygjc.ant.project.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewLocationActivity extends Activity {

    @Bind(R.id.back)
    ImageButton back;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_sendlocation)
    EditText etSendlocation;
    @Bind(R.id.ll_location)
    LinearLayout llLocation;
    String telRegex = "[1][3578]\\d{9}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);
        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String phone = etPhone.getText().toString();
                final String location = etSendlocation.getText().toString();
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(UIUtils.getContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(phone)) {
                        Toast.makeText(UIUtils.getContext(), "电话号码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        boolean matches = phone.matches(telRegex);
                        if (!matches) {
                            Toast.makeText(UIUtils.getContext(), "请正确填写手机号", Toast.LENGTH_SHORT).show();
                            return;
                        }

                }

                if (TextUtils.isEmpty(location)) {
                    Toast.makeText(UIUtils.getContext(), "地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                LocationManager locationManager=new LocationManager(UIUtils.getContext());
                locationManager.insert(name,phone,location);
                finish();
            }
        });
    }
}
