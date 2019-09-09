package com.mvc.dresssup.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvc.dresssup.R;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.OrderDetailBean;
import com.mvc.dresssup.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/11/7.
 */

public class OrderDetailAdapter extends BaseAdapter {
    private List<OrderDetailBean.ProductsBean> Products;
    private Context context;

    private AddCartListener addCartListener;

    public interface AddCartListener {
        void addCart(View view, int position);
    }

    public void setAddCartListener(AddCartListener addCartListener) {
        this.addCartListener = addCartListener;
    }

    public OrderDetailAdapter(Context context, List<OrderDetailBean.ProductsBean> products) {
        this.context = context;
        Products = products;
    }

    @Override
    public int getCount() {
        return Products.size();
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
            convertView = View.inflate(UIUtils.getContext(), R.layout.item_product_detail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Constants.Picture_URL1 + Products.get(position).getPicture()).into(holder.ivProduct);
        holder.productName.setText(Products.get(position).getName());
        holder.tvPrice.setText("￥" + Products.get(position).getSalePrice());
        holder.productNum.setText("数量:" + Products.get(position).getNum());

        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addCartListener != null) {
                    addCartListener.addCart(view, position);
                }
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_product)
        ImageView ivProduct;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.product_num)
        TextView productNum;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.add_cart)
        TextView addCart;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
