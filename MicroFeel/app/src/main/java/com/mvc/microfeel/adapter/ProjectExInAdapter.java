package com.mvc.microfeel.adapter;

import android.content.Context;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mvc.microfeel.R;
import com.mvc.microfeel.domain.ExInProject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/9/1.
 */

public class ProjectExInAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ExInProject> projectExIN;

    private int clickCount=0;

    public ProjectExInAdapter(Context context, ArrayList<ExInProject> projectExIN) {
        this.context = context;
        this.projectExIN = projectExIN;
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_ex_in, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.projectDetail.setText(projectExIN.get(position).getExplanation());
        TextPaint tp = holder.tvMoney.getPaint();
        tp.setFakeBoldText(true);
      if(projectExIN.get(position).getAmount()<0){
          String format = new DecimalFormat("#.00").format(projectExIN.get(position).getAmount());
          System.out.println(format);
          holder.tvMoney.setText("￥"+format);
      }else{
          NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
          // 把转换后的货币String类型返回  
          String format1 = format.format(projectExIN.get(position).getAmount());
          holder.tvMoney.setText(format1);
      }



        holder.projectTime.setText(projectExIN.get(position).getDate());

        holder.projectDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount=clickCount+1;
                if(clickCount%2==1){
                    showCheckAll(holder.projectDetail);
                }else{
                    showSingle(holder.projectDetail);
                }

            }
        });

        return convertView;
    }

   class ViewHolder {
        @Bind(R.id.projectDetail)
        TextView projectDetail;
        @Bind(R.id.projectTime)
        TextView projectTime;
        @Bind(R.id.tv_money)
        TextView tvMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 如果TextView没有显示完全就显示查看全部按钮，点击查看全部按钮后TextView会显示全部内容，同时隐藏查看全部按钮
     * @param tv_content
     */
    private void showCheckAll(final TextView tv_content) {
        Layout l = tv_content.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0) {
                if (l.getEllipsisCount(lines - 1) > 0) {
                            //显示TextView的全部内容其实就是取消这类隐藏文字的属性
                            tv_content.setEllipsize(null);
                                tv_content.setSingleLine(false);
                }
            }
        }
    }

    /**
     * 隐藏行数
     * @param tv_content
     */
    private void showSingle(final TextView tv_content) {
        Layout l = tv_content.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 2) {
                    tv_content.setEllipsize(TextUtils.TruncateAt.END);
                    tv_content.setLines(2);
            }
        }
    }
}
