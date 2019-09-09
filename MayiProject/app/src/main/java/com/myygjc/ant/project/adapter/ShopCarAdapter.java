package com.myygjc.ant.project.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.doman.ShopDetail;
import com.myygjc.ant.project.util.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/2/17.
 */

public class ShopCarAdapter extends BaseAdapter {
    private ArrayList<ShopDetail> data;

    private ArrayList<Integer> selectedPostion=new ArrayList<>();
    // 用来控制CheckBox的选中状况
    private HashMap<Integer, Boolean> isSelected;

    public ShopCarAdapter(ArrayList<ShopDetail> data) {
        this.data = data;
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(UIUtils.getContext(), R.layout.shop_car, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvShopname.setText(data.get(data.size()-position-1).getName());
        holder.tvShopprice.setText(data.get(data.size()-position-1).getPrice());
        holder.tvCount.setText(""+data.get(data.size()-position-1).getCount());
        if(getIsSelected()!=null) {
            holder.cbSigle.setChecked(getIsSelected().get(position));
        }

        // 监听checkBox并根据原来的状态来设置新的状态
        holder.cbSigle.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (isSelected.get(position)) {
                    isSelected.put(position, false);
                    setIsSelected(isSelected);
                } else {
                    isSelected.put(position, true);
                    setIsSelected(isSelected);
                }
                getChoiceData();

            }
        });
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.cb_sigle)
        CheckBox cbSigle;
        @Bind(R.id.tv_shopname)
        TextView tvShopname;
        @Bind(R.id.tv_shopprice)
        TextView tvShopprice;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.ll_shop_detail)
        RelativeLayout llShopDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public List<Integer> getChoiceData() {
        for (Map.Entry<Integer, Boolean> entry : isSelected.entrySet()) {
            if (entry.getValue().equals(true)) {
                selectedPostion.add(entry.getKey());
            }
        }
        return selectedPostion;
    }
}
