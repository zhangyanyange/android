package com.microfeel.meiquetiliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.microfeel.meiquetiliao.util.DateUtils;

import java.util.ArrayList;

import static com.microfeel.meiquetiliao.util.UIUtils.getResources;

/**
 * Created by zy2 on 2017/12/18.
 */
public class LookPhotoAdapter extends BaseAdapter {
    private ArrayList<String>photo;
    private Context context;


    public LookPhotoAdapter(Context context,  ArrayList<String> photo) {
        this.context = context;
        this.photo = photo;
    }

    @Override
    public int getCount() {
        return photo.size();
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
    public View getView(final int position, View converView, ViewGroup viewGroup) {
        PhotoView p = new PhotoView(context);
        p.setLayoutParams(new AbsListView.LayoutParams((int) (getResources().getDisplayMetrics().density * 100), (int) (getResources().getDisplayMetrics().density * 100)));
        p.setScaleType(ImageView.ScaleType.CENTER_CROP);
       Glide.with(context).load(photo.get(position)).signature(new StringSignature(DateUtils.GetNowTime())).into(p);
        // 把PhotoView当普通的控件把触摸功能关掉
        p.disenable();
        return p;

    }
}
