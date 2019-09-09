package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.MoneyHas;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/2/22.
 */

public class MoneyHasProcotol {
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
    public  MoneyHas getMoney(final String userId){
        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetAmount?客户核算Id="+userId).build().execute();
            if(response.isSuccessful()) {

                String string = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(string, MoneyHas.class);
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
            e.printStackTrace();
            return null;
        }
    }
}
