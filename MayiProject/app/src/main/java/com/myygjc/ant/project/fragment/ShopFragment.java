package com.myygjc.ant.project.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myygjc.ant.project.R;
import com.myygjc.ant.project.activity.LoginActivity;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.AddMaterialCar;
import com.myygjc.ant.project.doman.MessageEvent;
import com.myygjc.ant.project.doman.MoneyHas;
import com.myygjc.ant.project.doman.UserId;
import com.myygjc.ant.project.procotol.AddCarProcotol;
import com.myygjc.ant.project.procotol.GetMaterialPriceProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.ToastUtils;
import com.myygjc.ant.project.util.UIUtils;
import com.myygjc.ant.project.view.AmountView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/3/29.
 */

public class ShopFragment extends Fragment {
    @Bind(R.id.amount_view)
    AmountView amountView;
    private int id;
    private int total;
    private String aa1;
    private String name;
    private double price;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.tv_shopname)
    TextView tvShopname;
    @Bind(R.id.tv_price)
    TextView tvPrice;

    @Bind(R.id.add_shopcar)
    TextView addShopcar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_shop, null);

        EventBus.getDefault().register(this);

        ButterKnife.bind(this, view);

        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();

        lp.width = screenWidth;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        imageView.setLayoutParams(lp);
        imageView.setMaxWidth(screenWidth);
        imageView.setMaxHeight((int) (screenWidth * 9 / 16));
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        name = bundle.getString("name");
        price = bundle.getDouble("price");
        String tp = bundle.getString("tp");
        total = bundle.getInt("total");
        tvShopname.setText(name);
        // 想要转换成指定国家的货币格式  
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // 把转换后的货币String类型返回  
        aa1 = format.format(price);
        tvPrice.setText(aa1);
//        spn.setMaxValue(total);
        amountView.setGoods_storage(total);
        Glide
                .with(UIUtils.getContext())// 指定Context
                .load("http://123.206.107.160" + tp) //指定图片URL
                .placeholder(R.drawable.zhanweitu1) //指定图片未加载成功前显示的图片
                .error(R.drawable.zhanweitu1)// 指定图片加载失败显示的图片
                .into(imageView); // 指定显示图片的ImageView

        addShopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 首先进行是否登陆判断
                if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
                    new Thread() {
                        @Override
                        public void run() {
                            AddMaterialCar materialCar = new AddMaterialCar();
                            materialCar.setHSID(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                            materialCar.setWlID(id);
                            materialCar.setQty(amountView.getAmount());
                            materialCar.setPrice(price);
                            UserId userId = AddCarProcotol.getData(materialCar);

                            if (userId.getResult() == 0) {
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToastInCenter(UIUtils.getContext(), 2, "恭喜您，商品已添加至购物车!", Toast.LENGTH_SHORT);
                                    }
                                });
                            } else {
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToastInCenter(UIUtils.getContext(), 1, "加入购物车失败!", Toast.LENGTH_SHORT);
                                    }
                                });
                            }
                        }
                    }.start();
                } else {
                    Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });


        return view;

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(MessageEvent messageEvent) {
        new Thread() {
            @Override
            public void run() {
                MoneyHas moneyHas = GetMaterialPriceProcotol.delectData(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")), id);
                NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
                // 把转换后的货币String类型返回  
                final String loginPrice = format.format(moneyHas.getContentResult());

                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        tvPrice.setText(loginPrice);
                    }
                });
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (ToastUtils.getCenterToast() != null) {
            ToastUtils.cancle();
        }
        ButterKnife.unbind(this);
    }

}
