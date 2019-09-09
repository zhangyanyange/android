package com.microfeel.meiquetiliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.domain.Material;
import com.microfeel.meiquetiliao.util.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/5.
 */
public class MaterialzAdapter extends BaseAdapter {
    private OnAddCartListener onAddCartListener;
    public interface OnAddCartListener{
        void addCart(int position);
    }

    public void setOnAddCartListener(OnAddCartListener onAddCartListener) {
        this.onAddCartListener = onAddCartListener;
    }

    private Context context;
    private List<Material.ListContentBean> listContentBeen;

    public MaterialzAdapter(Context context, List<Material.ListContentBean> listContentBeen) {
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (!listContentBeen.get(position).isIsDetail()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        int type = getItemViewType(position);
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 =null;
        if (convertView == null) {
            switch (type) {
                case 0:
                    convertView = View.inflate(UIUtils.getContext(), R.layout.item_materials, null);
                    viewHolder1=new ViewHolder1(convertView);
                    convertView.setTag(viewHolder1);
                    break;
                case 1:
                    convertView = View.inflate(UIUtils.getContext(), R.layout.item_materialz, null);
                    viewHolder2=new ViewHolder2(convertView);
                    convertView.setTag(viewHolder2);
                    break;
            }

        }else{
            switch (type){
                case 0:
                    viewHolder1= (ViewHolder1) convertView.getTag();
                    break;
                case 1:
                    viewHolder2= (ViewHolder2) convertView.getTag();
                    break;
            }
        }
        switch (type){
            case 0:
                viewHolder1.tvMaterialType.setText(listContentBeen.get(position).getName());
                break;
            case 1:
                viewHolder2.tvMaterialName.setText(listContentBeen.get(position).getName());
                viewHolder2.tvPrice.setText(listContentBeen.get(position).getPrice()+"/"+listContentBeen.get(position).getUnit());
                viewHolder2.shoppingcat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onAddCartListener!=null){
                            onAddCartListener.addCart(position);
                        }
                    }
                });
                break;
        }

        return convertView;
    }



    static class ViewHolder1 {
        @Bind(R.id.tv_material_type)
        TextView tvMaterialType;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 {
        @Bind(R.id.tv_material_name)
        TextView tvMaterialName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.shoppingcat)
        ImageButton shoppingcat;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
