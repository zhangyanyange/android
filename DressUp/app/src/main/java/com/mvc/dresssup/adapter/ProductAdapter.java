package com.mvc.dresssup.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvc.dresssup.R;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.ClassificationProduct;
import com.mvc.dresssup.util.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/10/26.
 */

public class ProductAdapter extends BaseAdapter {

    private ArrayList<ClassificationProduct.ProductsBean> data;

    private Context context;

    public ProductAdapter(ArrayList<ClassificationProduct.ProductsBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(UIUtils.getContext(), R.layout.item_products_list, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context)
                .load(Constants.Picture_URL1 + data.get(position).getPicture())
                .into(holder.ivProduct);

        holder.productName.setText(data.get(position).getName());
        String salePrice = data.get(position).getSalePrice();
        holder.salePrice.setText(salePrice);
        holder.marketPrice.getPaint().setAntiAlias(true);
        holder.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        String marketPrice = data.get(position).getMarketPrice();
        holder.marketPrice.setText(marketPrice);
        holder.tvStock.setText("仅剩" + data.get(position).getStock() + "件");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_product)
        ImageView ivProduct;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.sale_price)
        TextView salePrice;
        @BindView(R.id.market_price)
        TextView marketPrice;
        @BindView(R.id.tv_stock)
        TextView tvStock;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
