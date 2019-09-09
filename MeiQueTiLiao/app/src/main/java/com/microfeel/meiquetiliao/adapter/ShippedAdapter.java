package com.microfeel.meiquetiliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.domain.Shipped;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/7.
 */

public class ShippedAdapter extends BaseAdapter {
    private Context context;
    private List<Shipped.ListContentBean> listContentBeen;

    public ShippedAdapter(Context context, List<Shipped.ListContentBean> listContentBeen) {
        this.context = context;
        this.listContentBeen = listContentBeen;
    }

    @Override
    public int getCount() {
        return listContentBeen.size();
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
            convertView = View.inflate(context, R.layout.item_shipped, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(listContentBeen.get(position).getDate().contains("9999")){
            holder.sendData.setText("配送日期: ");
        }else{
            holder.sendData.setText("配送日期: "+listContentBeen.get(position).getDate().substring(0,10));
        }
       holder.orderNumber.setText("单号: "+listContentBeen.get(position).getCode());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.sendData)
        TextView sendData;
        @Bind(R.id.order_number)
        TextView orderNumber;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}