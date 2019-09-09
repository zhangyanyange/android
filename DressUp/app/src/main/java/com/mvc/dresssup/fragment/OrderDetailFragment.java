package com.mvc.dresssup.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mvc.dresssup.R;
import com.mvc.dresssup.adapter.OrderDetailAdapter;
import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.base.MyApplication;
import com.mvc.dresssup.domain.AddCar;
import com.mvc.dresssup.domain.CancelOrder;
import com.mvc.dresssup.domain.DeleteOrder;
import com.mvc.dresssup.domain.DeleteOrderResult;
import com.mvc.dresssup.domain.OrderDetailBean;
import com.mvc.dresssup.procotol.AddShopCarProcotol;
import com.mvc.dresssup.procotol.OrderCancelProtocol;
import com.mvc.dresssup.procotol.OrderDetailProtocol;
import com.mvc.dresssup.util.UIUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zy2 on 2017/11/6.
 */
public class OrderDetailFragment extends BaseFragment {


    @BindView(R.id.et_name)
    TextView etName;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.et_sendlocation)
    TextView etSendlocation;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.order_detail)
    ListView lvorderDetail;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.order_pay)
    TextView orderPay;
    @BindView(R.id.order_cancel)
    TextView orderCancel;
    @BindView(R.id.order_pay_complete)
    TextView orderPayComplete;
    @BindView(R.id.rl_zhifu)
    RelativeLayout rlZhifu;
    Unbinder unbinder;
    private OrderDetailBean orderDetail;
    private int id;

    @Override
    public LoadingPager.LoadedResult initData() {
        Bundle bundle = getArguments();
        id = bundle.getInt("Id");
        orderDetail = OrderDetailProtocol.getOrderDetailProtocol(id);

        return checkResult(orderDetail);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_order_detail, null);
        ButterKnife.bind(this, view);

        etName.setText(orderDetail.getConsignee());
        etPhone.setText(orderDetail.getTelephone());
        etSendlocation.setText(orderDetail.getAddress());

        orderNumber.setText(orderDetail.getOrderNo());
        orderPay.setText("微信支付");
        orderTime.setText(orderDetail.getCreatDate());
        if (orderDetail.getStatus() == 0) {
            rlZhifu.setVisibility(View.VISIBLE);
        } else {
            rlZhifu.setVisibility(View.GONE);
        }
        orderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DeleteOrder deleteOrder = new DeleteOrder();
                deleteOrder.setId(id);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DeleteOrderResult result = OrderCancelProtocol.cancelOrder(deleteOrder);
                        if (result != null && result.getResult() == 1) {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().finish();
                                    Toast.makeText(UIUtils.getContext(), "取消成功", Toast.LENGTH_SHORT).show();
                                    EventBus.getDefault().post(new CancelOrder());
                                }
                            });
                        } else {
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UIUtils.getContext(), "取消失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();


            }
        });


        OrderDetailAdapter adapter = new OrderDetailAdapter(getActivity(), orderDetail.getProducts());
        lvorderDetail.setAdapter(adapter);
        adapter.setAddCartListener(new OrderDetailAdapter.AddCartListener() {
            @Override
            public void addCart(View view, int position) {
                AddCar car = new AddCar();
                car.setId(orderDetail.getProducts().get(position).getId());
                car.setNum(1);
                final String s = new Gson().toJson(car);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String addshopcar = AddShopCarProcotol.addshopcar(s);
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                if (addshopcar == null) {
                                    Toast.makeText(UIUtils.getContext(), "添加购物车失败", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UIUtils.getContext(), "添加购物车成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        return view;
    }


}
