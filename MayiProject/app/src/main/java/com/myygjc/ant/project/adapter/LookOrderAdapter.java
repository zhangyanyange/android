package com.myygjc.ant.project.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myygjc.ant.project.R;
import com.myygjc.ant.project.doman.LookOrder;
import com.myygjc.ant.project.util.UIUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/3/6.
 */

public class LookOrderAdapter extends BaseAdapter {

    private List<LookOrder> data;

    public LookOrderAdapter(List<LookOrder> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data.size() != 0) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(UIUtils.getContext(), R.layout.item_shop_detail1, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide
                .with(UIUtils.getContext())// 指定Context
                .load("http://123.206.107.160"+data.get(position).getBz()) //指定图片URL
                .placeholder(R.drawable.zhanwei) //指定图片未加载成功前显示的图片
                .error(R.drawable.zhanwei)// 指定图片加载失败显示的图片
                .into(holder.iv); // 指定显示图片的ImageView



        holder.tvShopname.setText(data.get(position).getName());
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        holder.tvPrice.setText(format.format(data.get(position).getDj()));
        holder.tvSpec.setText(""+data.get(position).getSl());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_shopname)
        TextView tvShopname;
        @Bind(R.id.tv_spec)
        TextView tvSpec;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.ll_shop_detail)
        LinearLayout llShopDetail;
        @Bind(R.id.imageView2)
        ImageView iv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
