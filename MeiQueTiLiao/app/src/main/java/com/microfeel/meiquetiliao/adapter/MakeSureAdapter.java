package com.microfeel.meiquetiliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.domain.MentionMaterial;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/4.
 */
public class MakeSureAdapter extends BaseAdapter {
    private Context context;
    private List<MentionMaterial.ListContentBean> listContent;

    public MakeSureAdapter(Context context, List<MentionMaterial.ListContentBean> listContent) {
        this.context = context;
        this.listContent = listContent;
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
            convertView = View.inflate(context, R.layout.item_sure_material, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.sendData.setText("送货日期: "+listContent.get(position).getRequireTime().substring(0,10));
        String remark = listContent.get(position).getRemark();
        String[] split = remark.split("---");
        viewHolder.tvCangku.setText(split[0]);
        if(split.length==2){
            viewHolder.tvRemake.setText("用户备注: "+split[1]);
        }
        if(listContent.get(position).isIsSelfPick()){
            viewHolder.tvZiti.setText("自提");
        }else{
            viewHolder.tvZiti.setText("非自提");
        }

       return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.sendData)
        TextView sendData;
        @Bind(R.id.tv_cangku)
        TextView tvCangku;
        @Bind(R.id.tv_remake)
        TextView tvRemake;
        @Bind(R.id.tv_ziti)
        TextView tvZiti;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
