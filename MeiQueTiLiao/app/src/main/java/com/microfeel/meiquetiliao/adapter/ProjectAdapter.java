package com.microfeel.meiquetiliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.domain.GetProjectList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/8/31.
 */

public class ProjectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GetProjectList>data;

    public ProjectAdapter(Context context, ArrayList<GetProjectList> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int posotion) {
        return null;
    }

    @Override
    public long getItemId(int posotion) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_project, null);
            holder = new ViewHolder(convertView);
         convertView.setTag(holder);
        } else {
         holder=(ViewHolder)convertView.getTag();
        }

        holder.customName.setText(data.get(position).getCustomerName());
        holder.custonPhone.setText(data.get(position).getPhone().substring(0,11));
        holder.custonLocation.setText(data.get(position).getAddress());
        holder.createTime.setText(data.get(position).getCreateTime().substring(0,10));


        return convertView;
    }

     class ViewHolder {
        @Bind(R.id.customName)
        TextView customName;
        @Bind(R.id.custonPhone)
        TextView custonPhone;
        @Bind(R.id.custonLocation)
        TextView custonLocation;
         @Bind(R.id.createtime)
          TextView createTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
