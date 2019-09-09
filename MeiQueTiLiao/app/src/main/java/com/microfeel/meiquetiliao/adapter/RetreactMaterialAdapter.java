package com.microfeel.meiquetiliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.domain.RetreactMaterial;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/6.
 */

public class RetreactMaterialAdapter extends BaseAdapter {
    private ArrayList<RetreactMaterial.ListContentBean> retreactMaterial;
    private Context context;

    private AmountChangeListener amountChangeListener;
    private ShopCartAdapter.EditTextListener editTextListener;
    public interface EditTextListener{
        void edittext(int position);
    }

    public void setEditTextListener(ShopCartAdapter.EditTextListener editTextListener) {
        this.editTextListener = editTextListener;
    }

    public interface AmountChangeListener {
        void amountchange(int position);
    }




    public void setAmountChangeListener(AmountChangeListener amountChangeListener) {
        this.amountChangeListener = amountChangeListener;
    }

    public RetreactMaterialAdapter(Context context, ArrayList<RetreactMaterial.ListContentBean> retreactMaterial) {
        this.context = context;
        this.retreactMaterial = retreactMaterial;

    }


    @Override
    public int getCount() {
        return retreactMaterial.size();
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
    public View getView(final int positon, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_retreatmaterial, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.customName.setText(retreactMaterial.get(positon).getName());
        viewHolder.custonPhone.setText(retreactMaterial.get(positon).getPrice() + "/" + retreactMaterial.get(positon).getUnit());
        viewHolder.tvCountMax.setText("(最大可退货数量"+(int) retreactMaterial.get(positon).getCount()+")");
        viewHolder.tvCount.setText("x" + retreactMaterial.get(positon).getNumber());

        viewHolder.tvAdd.setTag(positon);
        viewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) viewHolder.tvAdd.getTag();
                int value = retreactMaterial.get(position).getNumber();
                if(value>=(int)retreactMaterial.get(position).getCount()){
                        return;
               }
                value=value+1;
                retreactMaterial.get(position).setNumber(value);
                viewHolder.tvCount.setText("x" + retreactMaterial.get(positon).getNumber());

                if (amountChangeListener != null) {
                    amountChangeListener.amountchange(positon);
                }
            }
        });

        viewHolder.tvLess.setTag(positon);
        viewHolder.tvLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = (int) viewHolder.tvLess.getTag();
                int value = retreactMaterial.get(position).getNumber();
                if (value <= 0) {
                    return;
                }
                value = value - 1;
                retreactMaterial.get(position).setNumber(value);

                viewHolder.tvCount.setText("x" + retreactMaterial.get(positon).getNumber());

                if (amountChangeListener != null) {
                    amountChangeListener.amountchange(positon);
                }
            }
        });
        viewHolder.tvCount.setTag(positon);

        viewHolder.tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position= (int) viewHolder.tvCount.getTag();
                if(editTextListener!=null){
                    editTextListener.edittext(position);
                }
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.customName)
        TextView customName;
        @Bind(R.id.custonPhone)
        TextView custonPhone;
        @Bind(R.id.tv_add)
        TextView tvAdd;
        @Bind(R.id.tv_less)
        TextView tvLess;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.tv_countMax)
         TextView tvCountMax;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
