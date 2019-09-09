package com.mvc.dresssup.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvc.dresssup.R;
import com.mvc.dresssup.activity.ClassificationActivity;
import com.mvc.dresssup.domain.ClassificationProduct;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/9/25.
 */

public class PinPaiAdapter extends BaseAdapter {
    private Context context;
    private List<ClassificationProduct.CategorysBean> bean;
    private final ClassificationActivity activity;


    public PinPaiAdapter(Context context, List<ClassificationProduct.CategorysBean> bean) {
        this.context = context;
        this.bean = bean;
        activity = (ClassificationActivity) context;
    }

    @Override
    public int getCount() {
        return bean.size();
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
    public View getView(int positon, View converView, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (converView == null) {
            converView = View.inflate(context, R.layout.item_pinpai, null);
            holder = new ViewHolder(converView);
            converView.setTag(holder);
        } else {
            holder = (ViewHolder) converView.getTag();
        }
        holder.tvPinpai.setText(bean.get(positon).getName());

        if (positon == activity.mPosition) {
            holder.rlDianji.setBackgroundColor(Color.parseColor("#FFA500"));
            holder.tvPinpai.setTextColor(Color.WHITE);
        } else {
            holder.rlDianji.setBackgroundColor(Color.WHITE);
            holder.tvPinpai.setTextColor(Color.BLACK);
        }
        return converView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_pinpai)
        TextView tvPinpai;
        @BindView(R.id.rl_dianji)
        RelativeLayout rlDianji;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
