package com.microfeel.meiquetiliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.domain.MaterialDetail;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/5.
 */
public class MaterialAdapter extends BaseAdapter {
    private Context context;
    private List<MaterialDetail.ListContentBean> listContent;
    private int qufen;
    public MaterialAdapter(Context context, List<MaterialDetail.ListContentBean> listContent,int qufen) {
        this.context = context;
        this.listContent = listContent;
        this.qufen=qufen;
    }

    @Override
    public int getCount() {
        return listContent.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_material, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvMaterialName.setText(listContent.get(position).getName());
        viewHolder.tvPrice.setText(listContent.get(position).getPrice()+"/"+listContent.get(position).getUnit());
        if(qufen==3){
            viewHolder.tvCount.setText("×"+(int)listContent.get(position).getCount());
        }else{
            viewHolder.tvCount.setText("×"+(int)listContent.get(position).getNumber());
        }


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_material_name)
        TextView tvMaterialName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_count)
        TextView tvCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
