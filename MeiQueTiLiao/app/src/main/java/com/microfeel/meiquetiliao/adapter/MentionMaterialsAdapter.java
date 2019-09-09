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
public class MentionMaterialsAdapter extends BaseAdapter {
    private Context context;
    private List<MentionMaterial.ListContentBean> listContent;
    private  onClickDeleteListener clickDeleteListener;
    public interface onClickDeleteListener{
        void delete(int position);
    };

    public void setClickDeleteListener(onClickDeleteListener clickDeleteListener) {
        this.clickDeleteListener = clickDeleteListener;
    }

    public MentionMaterialsAdapter(Context context, List<MentionMaterial.ListContentBean> listContent) {
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_madify_material, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.sendData.setText("送货日期: "+listContent.get(position).getRequireTime().substring(0,10));
        String remark = listContent.get(position).getRemark();

        String[] split = remark.split("---");
        viewHolder.tvCangku.setText(split[0]);
        if(split.length>=2){
            viewHolder.tvRemake.setText("用户备注: "+split[1]);
        }
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.tvDelete.setEnabled(false);
                if(clickDeleteListener!=null){
                    clickDeleteListener.delete(position);
                }
            }
        });

       return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.sendData)
        TextView sendData;
        @Bind(R.id.tv_cangku)
        TextView tvCangku;
        @Bind(R.id.tv_remake)
        TextView tvRemake;
        @Bind(R.id.tv_delete)
        TextView tvDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
