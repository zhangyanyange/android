package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.MaterialTypeDetail;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/1/13.
 */

public class MaterialTypeDetailProcotol {

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


    public List<MaterialTypeDetail> getData(String name, int id, String userId){
        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetMaterial?客户核算ID="+userId+"&名称="+name+"&pageIndex="+id+"&pageSize=15").build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                List<MaterialTypeDetail> mData=gson.fromJson(string,new TypeToken<List<MaterialTypeDetail>>(){}.getType());
                if(mData.size()!=0){
                    if(onNetFinish!=null){
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                onNetFinish.onSuccessListener();
                            }
                        });
                    }
                    }else{
                    if(onNetFail!=null){
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                onNetFail.onFailListener();
                            }
                        });
                    }
                }
                return mData;
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
