package com.mvc.dresssup.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvc.dresssup.R;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.OrderAllBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/11/3.
 */

public class OrderAdapter extends BaseAdapter {

    private ArrayList<OrderAllBean> orders;
    private Context context;
    private DeleteOrderListener deleteOrderListener;
    private OrderDetailListener orderDetailListener;

    public OrderAdapter(ArrayList<OrderAllBean> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    public interface DeleteOrderListener {
        void deleteOrder(View view, int position);
    }

    public interface OrderDetailListener {
        void orderDetail(View view, int position);
    }

    public void setDeleteOrderListener(DeleteOrderListener deleteOrderListener) {
        this.deleteOrderListener = deleteOrderListener;
    }

    public void setOrderDetailListener(OrderDetailListener orderDetailListener) {
        this.orderDetailListener = orderDetailListener;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.orderNumber.setText(orders.get(position).getOrderNo());
        holder.shopCount.setText("共" + (orders.get(position).getNum() + "件商品"));
        holder.realPay.setText("实付款￥" + (orders.get(position).getActual()));
        List<OrderAllBean.ProductsBean> products = orders.get(position).getProducts();
        for (int i = 0; i < products.size(); i++) {
            if (i == 0) {
                Glide.with(context).load(Constants.Picture_URL1 + products.get(i).getPicture()).into(holder.image1);
            } else if (i == 1) {
                Glide.with(context).load(Constants.Picture_URL1 + products.get(i).getPicture()).into(holder.image2);
            } else if (i == 2) {
                Glide.with(context).load(Constants.Picture_URL1 + products.get(i).getPicture()).into(holder.image3);
            } else if (i == 3) {
                Glide.with(context).load(Constants.Picture_URL1 + products.get(i).getPicture()).into(holder.image4);
            }
        }
        holder.ibOrderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteOrderListener != null) {
                    deleteOrderListener.deleteOrder(view, position);
                }
            }
        });

        holder.buyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderDetailListener != null) {
                    orderDetailListener.orderDetail(view, position);
                }
            }
        });
        if (orders.get(position).getStatus() == 0) {
            holder.orderStatus.setTextColor(Color.parseColor("#DD3830"));
            holder.orderStatus.setText("等待付款");
            holder.buyAgain.setText("去支付");
        } else if (orders.get(position).getStatus() == 3) {
            holder.orderStatus.setTextColor(Color.parseColor("#666666"));
            holder.orderStatus.setText("已取消");

            holder.buyAgain.setText("再次购买");
        } else if (orders.get(position).getStatus() == 2) {
            holder.orderStatus.setTextColor(Color.parseColor("#DD3830"));
            holder.orderStatus.setText("已完成");

            holder.buyAgain.setText("再次购买");
        } else if (orders.get(position).getStatus() == 1) {
            holder.orderStatus.setTextColor(Color.parseColor("#DD3830"));
            holder.orderStatus.setText("待收货");

            holder.buyAgain.setText("查看物流");
            holder.ibOrderDelete.setVisibility(View.GONE);
        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.order_number)
        TextView orderNumber;
        @BindView(R.id.order_status)
        TextView orderStatus;
        @BindView(R.id.ib_order_delete)
        ImageButton ibOrderDelete;
        @BindView(R.id.image1)
        ImageView image1;
        @BindView(R.id.image2)
        ImageView image2;
        @BindView(R.id.image3)
        ImageView image3;
        @BindView(R.id.image4)
        ImageView image4;
        @BindView(R.id.real_pay)
        TextView realPay;
        @BindView(R.id.shop_count)
        TextView shopCount;
        @BindView(R.id.buy_again)
        TextView buyAgain;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
