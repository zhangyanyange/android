package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.DelectMaterialCar;
import com.myygjc.ant.project.doman.UserId;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/2/21.
 */

public class DelectCarProcotol {
    private OnNetFinish onNetFinish;
    private OnNetFail onNetFail;
    public interface OnNetFinish{
        void onSuccessListener();
    }

    public interface OnNetFail{
        void onFailListener();
    }

    public void setOnNetFail(OnNetFail onNetFail) {
        this.onNetFail = onNetFail;
    }

    public void setOnSuccess(OnNetFinish onNetFinish) {
        this.onNetFinish= onNetFinish;
    }
    public UserId delectData(DelectMaterialCar delectMaterialCar){
        try {
            String s = new Gson().toJson(delectMaterialCar);
            Response response = OkHttpUtils
                    .postString()
                    .url("http://123.206.107.160/webapiJD/api/JD/DeleteCar")
                    .content(s)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute();
            if(response.isSuccessful()){
                if(onNetFinish!=null){
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            onNetFinish.onSuccessListener();
                        }
                    });
                }
                String string = response.body().string();
                Gson gson=new Gson();
                UserId data = gson.fromJson(string, UserId.class);
                return data;
            }else{
                if(onNetFail!=null){
                    MyApplication.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            onNetFail.onFailListener();
                        }
                    });
                }
                return null;
            }
        } catch (Exception e) {
            if(onNetFail!=null){
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        onNetFail.onFailListener();
                    }
                });
            }
            System.out.println("错误"+e.toString());
            return null;
        }
    }
}
