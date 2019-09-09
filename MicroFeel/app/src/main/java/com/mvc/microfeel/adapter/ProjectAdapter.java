package com.mvc.microfeel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mvc.microfeel.R;
import com.mvc.microfeel.domain.ProjectList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/8/31.
 */

public class ProjectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProjectList>data;

    public ProjectAdapter(Context context, ArrayList<ProjectList> data) {
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
        holder.custonPhone.setText(data.get(position).getCustomerPhone());
        holder.custonLocation.setText(data.get(position).getAdress());
        holder.createTime.setText(data.get(position).getCreateTime());
        if(data.get(position).getProjectType().equals("施工项目")){
            holder.projectType.setText("施工项目");
            holder.projectType.setTextColor(Color.parseColor("#EEB422"));
            holder.projectType.setBackgroundResource(R.drawable.type_shigong);
        }else if (data.get(position).getProjectType().equals("洽谈项目")){
            holder.projectType.setText("洽谈项目");
            holder.projectType.setTextColor(Color.parseColor("#00FFFF"));
            holder.projectType.setBackgroundResource(R.drawable.type_qitan);
        }else {
            holder.projectType.setText(data.get(position).getProjectType());
            holder.projectType.setTextColor(Color.parseColor("#FF0000"));
            holder.projectType.setBackgroundResource(R.drawable.type_else);
        }

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
         @Bind(R.id.projectType)
         TextView projectType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
