package com.microfeel.meiquetiliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.db.ShopCartManager;
import com.microfeel.meiquetiliao.domain.ShopCat;
import com.microfeel.meiquetiliao.util.PrefUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/6.
 */

public class ShopCartAdapter extends BaseAdapter {
    private ArrayList<ShopCat> shopCats;
    private Context context;
    private ShopCartManager manager;
    private DeleteListener deleteListener;
    private AmountChangeListener amountChangeListener;

    private EditTextListener editTextListener;
    public interface EditTextListener{
        void edittext(int position);
    }

    public void setEditTextListener(EditTextListener editTextListener) {
        this.editTextListener = editTextListener;
    }

    public interface AmountChangeListener {
        void amountchange(int position);
    }

    public interface DeleteListener {
        void delete(int position);
    }

    public void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setAmountChangeListener(AmountChangeListener amountChangeListener) {
        this.amountChangeListener = amountChangeListener;
    }

    public ShopCartAdapter(Context context, ArrayList<ShopCat> shopCats, ShopCartManager manager) {
        this.context = context;
        this.shopCats = shopCats;
        this.manager = manager;
    }


    @Override
    public int getCount() {
        return shopCats.size();
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
            convertView = View.inflate(context, R.layout.item_shopcart, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.customName.setText(shopCats.get(positon).getName());
        viewHolder.custonPhone.setText(shopCats.get(positon).getPrice() + "/" + shopCats.get(positon).getUnit());

        viewHolder.tvCount.setText("x" + shopCats.get(positon).getCount());

        viewHolder.tvAdd.setTag(positon);
        viewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) viewHolder.tvAdd.getTag();
                int value = shopCats.get(position).getCount() + 1;
                shopCats.get(position).setCount(value);
                 manager.update(value + "", PrefUtils.getString(context, "projectId", ""), shopCats.get(position).getMaterialid());
                viewHolder.tvCount.setText("x" + shopCats.get(positon).getCount());

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
                int value = shopCats.get(position).getCount();
                if (value <= 1) {
                    return;
                }
                value = value - 1;
                shopCats.get(position).setCount(value);
                manager.update(value + "", PrefUtils.getString(context, "projectId", ""), shopCats.get(position).getMaterialid());
                viewHolder.tvCount.setText("x"+ shopCats.get(positon).getCount());

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
        viewHolder.custonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteListener != null) {
                    deleteListener.delete(positon);
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
        @Bind(R.id.custonLocation)
        TextView custonLocation;
        @Bind(R.id.tv_add)
        TextView tvAdd;
        @Bind(R.id.tv_less)
        TextView tvLess;
        @Bind(R.id.tv_count)
        TextView tvCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
