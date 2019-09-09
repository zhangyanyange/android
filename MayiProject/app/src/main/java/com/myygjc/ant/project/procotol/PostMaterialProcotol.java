package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.MaterialPost;
import com.myygjc.ant.project.doman.ShopOrderInfo;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/1/18.
 */

public class PostMaterialProcotol {
    private PayOrderProcotol.OnNetFinish onNetFinish;
    public interface OnNetFinish{
        void onSuccessListener();
    }

    public void setOnSuccess(PayOrderProcotol.OnNetFinish onNetFinish) {
        this.onNetFinish= onNetFinish;
    }
    public  ShopOrderInfo verityCaptcha(MaterialPost catcha){

        try {
            String s = new Gson().toJson(catcha);
            System.out.println("提交订单号"+s);
            Response response = OkHttpUtils
                    .postString()
                    .url("http://123.206.107.160/webapiJD/api/JD/CreateNote")
                    .content(s)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute();
            if (response.isSuccessful()){

                if(onNetFinish!=null){
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            onNetFinish.onSuccessListener();
                        }
                    });
                }
                String string = response.body().string();
                System.out.println("失败"+string);
                Gson gson=new Gson();
                ShopOrderInfo data = gson.fromJson(string, ShopOrderInfo.class);
                return data;
            }else{
                if(onNetFinish!=null){
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            onNetFinish.onSuccessListener();
                        }
                    });
                }
                return null;
            }
        } catch (IOException e) {
            if(onNetFinish!=null){
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        onNetFinish.onSuccessListener();
                    }
                });
            }
            e.printStackTrace();
            return null;
        }
    }
}
