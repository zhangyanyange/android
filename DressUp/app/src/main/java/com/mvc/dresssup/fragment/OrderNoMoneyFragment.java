package com.mvc.dresssup.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mvc.dresssup.R;
import com.mvc.dresssup.activity.OrderDetailActivity;
import com.mvc.dresssup.adapter.OrderAdapter;
import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.base.MyApplication;
import com.mvc.dresssup.domain.CancelOrder;
import com.mvc.dresssup.domain.DeleteOrder;
import com.mvc.dresssup.domain.DeleteOrderResult;
import com.mvc.dresssup.domain.OrderAllBean;
import com.mvc.dresssup.procotol.DeleteOrderProcotol;
import com.mvc.dresssup.procotol.OrderListProcotol;
import com.mvc.dresssup.util.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/11/3.
 */

public class OrderNoMoneyFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.loadView)
    ProgressBar loadView;
    private ArrayList<OrderAllBean> orderList;
    private OrderAdapter adapter;

    @Override
    public LoadingPager.LoadedResult initData() {
        EventBus.getDefault().register(this);
        orderList = OrderListProcotol.getOrderList("0");

        return checkResult(orderList);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_order, null);
        ButterKnife.bind(this, view);

        adapter = new OrderAdapter(orderList, getActivity());
        listview.setAdapter(adapter);
        adapter.setDeleteOrderListener(new OrderAdapter.DeleteOrderListener() {
            @Override
            public void deleteOrder(View view, final int position) {
                loadView.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DeleteOrder deleteOrder=new DeleteOrder();
                        deleteOrder.setId(orderList.get(position).getId());

                        DeleteOrderResult result = DeleteOrderProcotol.deleteOrder(deleteOrder);
                        if(result!=null&&result.getResult()==1){
                            ArrayList<OrderAllBean> orderList1 = OrderListProcotol.getOrderList("0");
                           orderList.clear();
                            orderList.addAll(orderList1);
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UIUtils.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                    loadView.setVisibility(View.GONE);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }else{
                            MyApplication.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(UIUtils.getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                                    loadView.setVisibility(View.GONE);
                                }
                            });
                        }

                    }
                }).start();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                gotoOrderDetailActivity(position);
            }
        });
        adapter.setOrderDetailListener(new OrderAdapter.OrderDetailListener() {
            @Override
            public void orderDetail(View view, int position) {
                gotoOrderDetailActivity(position);
            }
        });
        return view;
    }

    private void gotoOrderDetailActivity(int position) {
        Intent intent=new Intent(UIUtils.getContext(), OrderDetailActivity.class);
        intent.putExtra("Id",orderList.get(position).getId());
        startActivity(intent);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(CancelOrder order) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                orderList.clear();
                final ArrayList<OrderAllBean> orderList1 = OrderListProcotol.getOrderList("0");
                orderList.addAll(orderList1);
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        orderList.addAll(orderList1);
                    }
                });
                adapter.notifyDataSetChanged();
            }
        }).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
