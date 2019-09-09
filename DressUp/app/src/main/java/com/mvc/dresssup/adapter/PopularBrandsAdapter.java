package com.mvc.dresssup.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvc.dresssup.R;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.PopularBrandsBean;
import com.mvc.dresssup.util.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/9/20.
 */

public class PopularBrandsAdapter extends BaseAdapter {
    private ArrayList<PopularBrandsBean> data;
    private Context context;

    public PopularBrandsAdapter(ArrayList<PopularBrandsBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(UIUtils.getContext(), R.layout.item_popularbrands, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.ivBrandPicture.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
//        holder.ivBrandPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(context)
                .load(Constants.Picture_URL1 + data.get(position).getPicture())
                .into(holder.ivBrandPicture);
        holder.brandName.setText(data.get(position).getName());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_brand_picture)
        ImageView ivBrandPicture;
        @BindView(R.id.brand_name)
        TextView brandName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
