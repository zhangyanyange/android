package com.mvc.dresssup.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mvc.dresssup.R;
import com.mvc.dresssup.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/9/20.
 */

public class CaiLiaoAdapter extends BaseAdapter {
    private int[] image = {R.drawable.jieju, R.drawable.putieservice, R.drawable.dengju, R.drawable.tushuaservice, R.drawable.tuliao, R.drawable.anzhuangservice, R.drawable.paitservice, R.drawable.clearseavice};

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public Object getItem(int i) {
        return image[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(UIUtils.getContext(), R.layout.cailiao, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cailiao.setLayoutParams(new RelativeLayout.LayoutParams(200, 200));
        holder.cailiao.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.cailiao.setPadding(20, 20, 20, 20);
        holder.cailiao.setImageResource(image[position]);
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.cailiao)
        ImageView cailiao;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
