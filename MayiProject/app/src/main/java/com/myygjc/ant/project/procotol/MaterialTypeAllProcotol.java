package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.base.MyApplication;
import com.myygjc.ant.project.doman.MaterialAll;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/1/13.
 */

public class MaterialTypeAllProcotol {

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


    public MaterialAll getData(String typeId, int id, String userId){
        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetMaterialDetail?客户核算Id="+userId+"&typeId="+typeId+"&pageIndex="+id+"&pageSize=10").build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                MaterialAll mData = gson.fromJson(string, MaterialAll.class);
//                if(mData.size()!=0){
//                    if(onNetFinish!=null){
//                        MyApplication.getHandler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                onNetFinish.onSuccessListener();
//                            }
//                        });
//                    }
//                    }else{
//                    if(onNetFail!=null){
//                        MyApplication.getHandler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                onNetFail.onFailListener();
//                            }
//                        });
//                    }
//                }
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
