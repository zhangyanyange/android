package com.myygjc.ant.project.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.doman.Producer;
import com.myygjc.ant.project.util.UIUtils;

import java.util.List;


public class FenleiAdapter3 extends BaseAdapter{

	private Context mContext;

	private int selectIndex = -1;
	private List<Producer> producer;

	public FenleiAdapter3(List<Producer> producer) {
		this.producer = producer;
	}

	@Override
	public int getCount() {
		return producer.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView=View.inflate(UIUtils.getContext(), R.layout.horizontal_list_item, null);
			holder.mTitle=(TextView)convertView.findViewById(R.id.text_list_item);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		if(producer.get(position).getName().length()>4){
			ViewGroup.LayoutParams lp = holder.mTitle.getLayoutParams();
			lp.width=UIUtils.dip2px(82);
			holder.mTitle.setLayoutParams(lp);
		}else if(producer.get(position).getName().length()==4){
			ViewGroup.LayoutParams lp = holder.mTitle.getLayoutParams();
			lp.width=UIUtils.dip2px(75);
			holder.mTitle.setLayoutParams(lp);
		}else if(producer.get(position).getName().length()==3){
			ViewGroup.LayoutParams lp = holder.mTitle.getLayoutParams();
			lp.width=UIUtils.dip2px(70);
			holder.mTitle.setLayoutParams(lp);
		}else{
			ViewGroup.LayoutParams lp = holder.mTitle.getLayoutParams();
			lp.width=UIUtils.dip2px(60);
			holder.mTitle.setLayoutParams(lp);
		}
		if(position == selectIndex){
			convertView.setSelected(true);
			holder.mTitle.setEnabled(false);
			holder.mTitle.setTextColor(Color.BLACK);
		}else{
			convertView.setSelected(false);
			holder.mTitle.setEnabled(true);
			holder.mTitle.setTextColor(Color.GRAY);
		}

		
		holder.mTitle.setText(producer.get(position).getName());

		return convertView;
	}

	private static class ViewHolder {
		private TextView mTitle ;
	}

	public void setSelectIndex(int i){
		selectIndex = i;
	}
}