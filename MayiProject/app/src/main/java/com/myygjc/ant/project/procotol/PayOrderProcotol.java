package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.PayOrder;
import com.myygjc.ant.project.doman.WxOrderInfo;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/2/28.
 */

public class PayOrderProcotol {
    private OnNetFinish onNetFinish;
    public interface OnNetFinish{
        void onSuccessListener();
    }

    public void setOnSuccess(OnNetFinish  onNetFinish) {
        this.onNetFinish= onNetFinish;
    }
    public  WxOrderInfo getOrderInfo(PayOrder order){

        try {
            String s = new Gson().toJson(order);
            Response response = OkHttpUtils
                    .postString()
                    .url("http://123.206.107.160/webapiJD/api/JD/ReqPay")
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
                System.out.println("秘密"+string);
                Gson gson = new Gson();
                WxOrderInfo data = gson.fromJson(string, WxOrderInfo.class);
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
