package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.GetCarBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/2/21.
 */

public class GetCarProcotol {
    private OnNetFinish onNetFinish;
    public interface OnNetFinish{
        void onSuccessListener();
    }

    public void setOnSuccess(OnNetFinish onNetFinish) {
        this.onNetFinish= onNetFinish;
    }
    public  ArrayList<GetCarBean> getData(String userId){
        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetCar?客户核算ID="+userId).build().execute();
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
                ArrayList<GetCarBean>  mData=gson.fromJson(string, new TypeToken<List<GetCarBean>>(){}.getType());
                return mData;
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
        } catch (Exception e) {
            if(onNetFinish!=null){
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        onNetFinish.onSuccessListener();
                    }
                });
            }
            System.out.println("错误"+e.toString());
            return null;
        }
    }


}
