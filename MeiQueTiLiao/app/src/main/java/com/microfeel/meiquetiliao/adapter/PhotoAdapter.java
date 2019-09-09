package com.microfeel.meiquetiliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.util.DateUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/18.
 */
public class PhotoAdapter extends BaseAdapter {
    private ArrayList<File> photo;
    private Context context;

    private DeletePhotoListener deletePhotoListener;
    public interface DeletePhotoListener{
        void deletephoto(int position);
    }

    public void setDeletePhotoListener(DeletePhotoListener deletePhotoListener) {
        this.deletePhotoListener = deletePhotoListener;
    }

    public PhotoAdapter(ArrayList<File> photo, Context context) {
        this.photo = photo;
        this.context = context;
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
        ViewHolder holder;
        if (converView == null) {
            converView = View.inflate(context, R.layout.item_photo, null);
            holder=new ViewHolder(converView);
            converView.setTag(holder);
        }else{
           holder= (ViewHolder) converView.getTag();
        }
        holder.ivPhone.setLayoutParams(new RelativeLayout.LayoutParams(350, 350));

        holder.ivPhone.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.ivPhone.setPadding(20,20,20,20);
        Glide.with(context).load(photo.get(position)).signature(new StringSignature(DateUtils.GetNowTime())).into(holder.ivPhone);

        holder.ivQuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deletePhotoListener!=null){
                    deletePhotoListener.deletephoto(position);
                }
            }
        });
        return converView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_phone)
        ImageView ivPhone;
        @Bind(R.id.quxiao)
        ImageView ivQuxiao;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
