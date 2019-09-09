package com.mvc.dresssup.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mvc.dresssup.R;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.AddCar;
import com.mvc.dresssup.domain.AddressBean;
import com.mvc.dresssup.domain.GoodsInfo;
import com.mvc.dresssup.domain.PayComplete;
import com.mvc.dresssup.domain.PostProducts;
import com.mvc.dresssup.domain.RefreshCar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

public class MakeSureOrderActivity extends Activity {


    double totalPrice;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.rl_fukuan)
    RelativeLayout rlFukuan;
    @BindView(R.id.et_name)
    TextView etName;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.et_sendlocation)
    TextView etSendlocation;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.order_money)
    TextView orderMoney;
    @BindView(R.id.post_order)
    TextView postOrder;
    @BindView(R.id.activity_make_sure_order)
    RelativeLayout activityMakeSureOrder;
    private AddressBean address;
    private ArrayList<GoodsInfo.CartsBean.ProductsBean> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_sure_order);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        final Intent intent = getIntent();
        address = intent.getParcelableExtra("address");
        products = intent.getParcelableArrayListExtra("isSelectedProduct");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        valueAddress(address);

        llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MakeSureOrderActivity.this, LocationChooseActivity.class);
                startActivity(intent1);
            }
        });
        for (int i = 0; i < products.size(); i++) {
            totalPrice += Double.parseDouble(products.get(i).getSalePrice()) * products.get(i).getNum();
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        orderMoney.setText(Html.fromHtml("<font color ='#000000'>合计金额:</font>" + "<font color ='#FF0000'>" + format.format(totalPrice) + "</font>"));

        postOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<AddCar> list = new ArrayList<AddCar>();
                PostProducts postProducts = new PostProducts();
                postProducts.setAddressId(address.getId());
                for (int i = 0; i < products.size(); i++) {
                    AddCar car = new AddCar();
                    car.setId(products.get(i).getId());
                    car.setNum(products.get(i).getNum());
                    list.add(car);
                }
                postProducts.setCartBindingModel(list);
                String s = new Gson().toJson(postProducts);
                OkHttpUtils.postString()
                        .url(Constants.BASE_URL + "Order/SaveOrder")
                        .content(s)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e) {
                                System.out.println(e.toString());
                            }

                            @Override
                            public void onResponse(String response) {


                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new RefreshCar());

                                        Intent intent1 = new Intent(MakeSureOrderActivity.this, PayOrderActivity.class);
                                        startActivity(intent1);
                                    }
                                }).start();
                            }
                        });
            }
        });
    }

    private void valueAddress(AddressBean address) {
        etName.setText(address.getConsignee());
        etPhone.setText(address.getTelephone());
        etSendlocation.setText(address.getAddress());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(AddressBean bean) {
        address = bean;
        valueAddress(bean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void payComplete(PayComplete payComplete) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
