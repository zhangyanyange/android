package com.mvc.dresssup.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mvc.dresssup.R;
import com.mvc.dresssup.domain.AddressBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/10/25.
 */

public class LocationChooiceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<AddressBean> data;

    private InnerItemOnclickListener mListener;


    public LocationChooiceAdapter(ArrayList<AddressBean> data, Context context) {
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
    public View getView(final int positon, View converView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (converView == null) {
            converView = View.inflate(context, R.layout.item_lcoation, null);
            holder = new ViewHolder(converView);
            converView.setTag(holder);
        } else {
            holder = (ViewHolder) converView.getTag();
        }

        holder.tvName.setText(data.get(positon).getConsignee());
        holder.tvTellphone.setText(data.get(positon).getTelephone());
        holder.tvLocation.setText(data.get(positon).getAddress());
        if (data.get(positon).isIsDefault() == 1) {
            holder.cbSigle.setBackgroundResource(R.drawable.chooice);
            holder.type.setBackgroundResource(R.drawable.type_red);
            holder.type.setTextColor(Color.parseColor("#3196DA"));
        } else {
            holder.cbSigle.setBackgroundResource(R.drawable.unchooice);
            holder.type.setBackgroundResource(R.drawable.type_grey);
            holder.type.setTextColor(Color.parseColor("#E5E5E5"));
        }

        holder.cbSigle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.itemClick(holder.cbSigle, positon);
                }
            }
        });

        return converView;
    }


    public interface InnerItemOnclickListener {
        void itemClick(View view, int position);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener) {
        this.mListener = listener;
    }

    static class ViewHolder {
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_tellphone)
        TextView tvTellphone;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.cb_sigle)
        View cbSigle;
        @BindView(R.id.textView3)
        TextView textView3;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
