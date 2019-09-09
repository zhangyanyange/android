package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myygjc.ant.project.doman.LookOrder;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/3/6.
 */

public class LookOrderProcotol {

    public static ArrayList<LookOrder> getOrderDetail(String id){
        try {

            Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetTLDMX?Id="+id).build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                ArrayList<LookOrder> mData=gson.fromJson(string, new TypeToken<List<LookOrder>>(){}.getType());
                return mData;
            }else{
                return null;
            }
        } catch (Exception e) {

            return null;
        }
    }
}
