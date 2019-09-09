package com.mvc.microfeel.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mvc.microfeel.R;
import com.mvc.microfeel.domain.ExInAllProject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/9/1.
 */

public class AllProjectExInAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ExInAllProject> projectExIN;

    private ArrayList<ExInAllProject>projectHte=new ArrayList<>();
    private ArrayList<ExInAllProject>projectYsk=new ArrayList<>();

    public AllProjectExInAdapter( Context context,ArrayList<ExInAllProject> projectExIN) {
        this.projectExIN = projectExIN;
        this.context = context;
    }

    @Override
    public int getCount() {
        return projectExIN.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        for (int i = 0; i < projectExIN.size()/2; i++) {
            projectHte.add(projectExIN.get(i));
        }
        for (int i = projectExIN.size()/2; i <projectExIN.size() ; i++) {
            projectYsk.add(projectExIN.get(i));
        }

        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_ex_in_all, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String format1 = format.format(projectExIN.get(position).getAmount());
        TextPaint tp = holder.projectMoney.getPaint();
        tp.setFakeBoldText(true);
        holder.projectMoney.setText(format1);
        DecimalFormat decimalFormat = new DecimalFormat("#.##%");
        if(projectExIN.get(position).getAccountName().contains("合同额")){
            if(!(projectExIN.get(position).getAmount()>0)){
                holder.projectName.setText(projectExIN.get(position).getAccountName()+"(0.00%)");
            }else{
                int z=position%projectYsk.size();
                String format2 = decimalFormat.format(projectYsk.get(z).getAmount() / projectHte.get(z).getAmount());
                holder.projectName.setText(projectExIN.get(position).getAccountName()+"("+format2+")");
            }
        }else{
            holder.projectName.setText(projectExIN.get(position).getAccountName());
        }


        return convertView;
    }


  class ViewHolder {
        @Bind(R.id.projectName)
        TextView projectName;
        @Bind(R.id.projectMoney)
        TextView projectMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
