package com.mvc.dresssup.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.mvc.dresssup.R;
import com.mvc.dresssup.activity.LoginActivity;
import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.base.MyApplication;
import com.mvc.dresssup.chat.ChatActivity;
import com.mvc.dresssup.chat.ChatConstant;
import com.mvc.dresssup.chat.DemoMessageHelper;
import com.mvc.dresssup.domain.AddCar;
import com.mvc.dresssup.domain.ProductDetail;
import com.mvc.dresssup.procotol.AddShopCarProcotol;
import com.mvc.dresssup.procotol.ProductDetailProcotol;
import com.mvc.dresssup.util.PrefUtils;
import com.mvc.dresssup.util.ToastUtils;
import com.mvc.dresssup.util.UIUtils;
import com.mvc.dresssup.view.AmountView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zy2 on 2017/10/30.
 */

public class ProductDetailFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.product_picture)
    ImageView productPicture;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.sale_price)
    TextView salePrice;
    @BindView(R.id.market_price)
    TextView marketPrice;
    @BindView(R.id.zhekou)
    TextView zhekou;
    @BindView(R.id.buy_count)
    TextView buyCount;
    @BindView(R.id.amount_view)
    AmountView amountView;
    @BindView(R.id.connet_kefu)
    TextView connetKefu;
    @BindView(R.id.add_car)
    TextView addCar;
    @BindView(R.id.loadView)
    ProgressBar loadView;
    Unbinder unbinder;
    private ProductDetail productDetail;

    @Override
    public LoadingPager.LoadedResult initData() {
        Bundle bundle = getArguments();
        String productId = bundle.getString("productId");

        productDetail = ProductDetailProcotol.getProductDetail(productId);
        return checkResult(productDetail);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_product_detail, null);
        ButterKnife.bind(this, view);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        Glide
                .with(UIUtils.getContext())
                .load(Constants.Picture_URL1 + productDetail.getPicture())
                .into(productPicture);
        salePrice.setText(productDetail.getSalePrice());
        marketPrice.getPaint().setAntiAlias(true);
        marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        marketPrice.setText(productDetail.getMarketPrice());
//        zhekou.setText(productDetail.getDiscount());
        buyCount.setText("已售:" + productDetail.getSales());
        productName.setText(productDetail.getName());
        amountView.setGoods_storage(productDetail.getStock());

        if (productDetail.getStock() <= 0) {
            addCar.setBackgroundColor(Color.GRAY);
            addCar.setEnabled(false);
        } else {
            addCar.setBackgroundColor(Color.parseColor("#F5B950"));
            addCar.setEnabled(true);
        }
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PrefUtils.getBoolean(UIUtils.getContext(), "isLogin", false)) {
                    Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                loadView.setVisibility(View.VISIBLE);
                AddCar car = new AddCar();
                car.setId(productDetail.getId());
                car.setNum(amountView.getAmount());
                final String s = new Gson().toJson(car);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String addshopcar = AddShopCarProcotol.addshopcar(s);
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                loadView.setVisibility(View.GONE);
                                if (addshopcar == null) {
                                    ToastUtils.showToastInCenter(UIUtils.getContext(), 1, "加入购物车失败!", 1000);
                                } else {
                                    ToastUtils.showToastInCenter(UIUtils.getContext(), 2, "恭喜您，商品已添加至购物车!", 1000);
                                }
                            }
                        });
                    }
                }).start();

            }
        });

        //联系客服
        connetKefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PrefUtils.getBoolean(UIUtils.getContext(), "isLogin", false)) {
                    Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                //模拟客服登录
                ChatClient.getInstance().register("zddcccdsz", "123", new Callback() {
                    @Override
                    public void onSuccess() {
                        ChatClient.getInstance().login("zddcccdsz", "123", new Callback() {
                            @Override
                            public void onSuccess() {
                                if (ChatClient.getInstance().isLoggedInBefore()) {
                                    //已经登录，可以直接进入会话界面
                                    System.out.println("登陆成功");
                                } else {
                                    //未登录，需要登录后，再进入会话界面
                                }
                            }

                            @Override
                            public void onError(int i, String s) {

                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });


                if (ChatClient.getInstance().isLoggedInBefore()) {
                    toChatActivity();
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        if (ToastUtils.getCenterToast() != null) {
            ToastUtils.cancle();
        }
        super.onDestroy();
    }

    private void toChatActivity() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putInt(ChatConstant.INTENT_CODE_IMG_SELECTED_KEY, 0);
                bundle.putParcelable("productDetail", productDetail);
                //设置点击通知栏跳转事件
//                Conversation conversation = ChatClient.getInstance().chatManager().getConversation(Preferences.getInstance().getCustomerAccount());
                String titleName = null;
//                if (conversation.officialAccount() != null){
//                    titleName = conversation.officialAccount().getName();
//                }
                // 进入主页面
                Intent intent = new IntentBuilder(getActivity())
                        .setTargetClass(ChatActivity.class)
                        .setVisitorInfo(DemoMessageHelper.createVisitorInfo())
                        .setServiceIMNumber("kefuchannelimid_691144")
                        .setTitleName(titleName)
//						.setScheduleAgent(DemoMessageHelper.createAgentIdentity("ceshiok1@qq.com"))
                        .setShowUserNick(true)
                        .setBundle(bundle)
                        .build();
                startActivity(intent);
            }
        });
    }


}
