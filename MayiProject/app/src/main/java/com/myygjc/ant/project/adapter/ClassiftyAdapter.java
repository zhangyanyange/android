package com.myygjc.ant.project.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.doman.Producer;
import com.myygjc.ant.project.util.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/3/16.
 */

public class ClassiftyAdapter extends BaseAdapter {
    private List<Producer> producers;

    public ClassiftyAdapter(List<Producer> producers) {
        this.producers = producers;
    }

    @Override
    public int getCount() {
        if (producers!=null) {
            return producers.size();
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
            convertView = View.inflate(UIUtils.getContext(), R.layout.item_classifty, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.classiftyName.setText(producers.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.classifty_name)
        TextView classiftyName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
