package com.myygjc.ant.project.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.util.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/1/12.
 */

public class GridAdapter extends BaseAdapter {

    private List<String> data;

    public GridAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
            convertView = View.inflate(UIUtils.getContext(), R.layout.hot_search_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.hotItemBtn.setText(data.get(position));
        return convertView;
    }

   class ViewHolder {
        @Bind(R.id.hot_item_btn)
        TextView hotItemBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
