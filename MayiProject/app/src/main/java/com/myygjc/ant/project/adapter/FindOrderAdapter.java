package com.myygjc.ant.project.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.doman.FindOrder;
import com.myygjc.ant.project.util.UIUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/3/6.
 */

public class FindOrderAdapter extends BaseAdapter {
    private List<FindOrder> data;

    public FindOrderAdapter(List<FindOrder> data) {
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
            convertView = View.inflate(UIUtils.getContext(), R.layout.item_find_order, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvOrdernumber.setText(data.get(position).getYwdjh());
        holder.tvTime.setText(data.get(position).getScsj().substring(0,10));
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // 把转换后的货币String类型返回  
        String price = format.format(data.get(position).getJe()+data.get(position).getCzyf()+data.get(position).getSpyf()+data.get(position).getEcdyyf());
        holder.tvMoney.setText(price);
        if(data.get(position).getZt()==6){
            holder.tvState.setText("支付取消");
            holder.tvState.setTextColor(Color.GRAY);
        }else if(data.get(position).getZt()==7){
            holder.tvState.setText("支付完成");
            holder.tvState.setTextColor(Color.RED);
        }else if(data.get(position).getZt()==1){
            holder.tvState.setText("等待受理");
            holder.tvState.setTextColor(Color.RED);
        }else if(data.get(position).getZt()==2){
            holder.tvState.setText("待配送");
            holder.tvState.setTextColor(Color.RED);
        }else if(data.get(position).getZt()==3){
            holder.tvState.setText("正在配送");
            holder.tvState.setTextColor(Color.RED);
        }else if(data.get(position).getZt()==3){
            holder.tvState.setText("订单完成");
            holder.tvState.setTextColor(Color.RED);
        }
        return convertView;
    }

   static class ViewHolder {
        @Bind(R.id.tv_ordernumber)
        TextView tvOrdernumber;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.tv_state)
        TextView tvState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
