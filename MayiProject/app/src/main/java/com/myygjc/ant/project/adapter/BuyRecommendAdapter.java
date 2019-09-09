package com.myygjc.ant.project.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myygjc.ant.project.R;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.AddMaterialCar;
import com.myygjc.ant.project.doman.RecommendBuy;
import com.myygjc.ant.project.doman.UserId;
import com.myygjc.ant.project.procotol.AddCarProcotol;
import com.myygjc.ant.project.util.DesUtils;
import com.myygjc.ant.project.util.PrefUtils;
import com.myygjc.ant.project.util.ToastUtils;
import com.myygjc.ant.project.util.UIUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/1/13.
 */

public class BuyRecommendAdapter extends BaseAdapter {

    public List<RecommendBuy> data;

    public BuyRecommendAdapter(List<RecommendBuy> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(UIUtils.getContext(), R.layout.item_shop_detail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvShopname.setText(data.get(position).getName());
        if (data.get(position).getSpec() == null || data.get(position).getSpec().equals("")) {
            holder.tvSpec.setText("未知");
        } else {
            holder.tvSpec.setText(data.get(position).getSpec());
        }
        // 想要转换成指定国家的货币格式  
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // 把转换后的货币String类型返回  
        String price = format.format(data.get(position).getPrice());

        holder.tvPrice.setText(price + "/" + data.get(position).getUnit());
        if (("" + data.get(position).getQty()).equals("0.0")) {
            holder.tvShopname.setTextColor(Color.GRAY);
            holder.ivAddcar.setVisibility(View.GONE);
           /* holder.llShopDetail.setBackgroundResource(R.color.grey300);*/
        } else {
           /* holder.llShopDetail.setBackgroundResource(R.color.write);*/
            holder.tvShopname.setTextColor(Color.BLACK);
            holder.ivAddcar.setVisibility(View.VISIBLE);
        }

        String url = "http://123.206.107.160" + data.get(position).getTp();

        Glide.with(UIUtils.getContext()).
                load(url)
                .error(R.drawable.zhanwei)
                .into(holder.shoptupian);

        holder.ivAddcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefUtils.getBoolean(UIUtils.getContext(), "islogin", false)) {
                    new Thread() {
                        @Override
                        public void run() {
                            AddMaterialCar materialCar=new AddMaterialCar();
                            materialCar.setHSID(PrefUtils.getString(UIUtils.getContext(), "userId", DesUtils.encryptDes("0")));
                            materialCar.setWlID(data.get(position).getId());
                            materialCar.setQty(1);
                            materialCar.setPrice(data.get(position).getPrice());
                            UserId userId = AddCarProcotol.getData(materialCar);

                            if (userId.getResult() == 0) {
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToastInCenter(UIUtils.getContext(), 2, "恭喜您，商品已添加至购物车!", Toast.LENGTH_SHORT);
                                    }
                                });
                            } else {
                                MyApplication.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToastInCenter(UIUtils.getContext(), 1, "加入购物车失败!", Toast.LENGTH_SHORT);
                                    }
                                });
                            }
                        }
                    }.start();
                } else {
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UIUtils.getContext(),"请进行登录",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_shopname)
        TextView tvShopname;
        @Bind(R.id.tv_spec)
        TextView tvSpec;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.ll_shop_detail)
        LinearLayout llShopDetail;
        @Bind(R.id.iv_addcar)
        ImageView ivAddcar;
        @Bind(R.id.shoptupian)
        ImageView shoptupian;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
