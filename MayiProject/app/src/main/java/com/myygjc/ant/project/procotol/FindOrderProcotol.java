package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myygjc.ant.project.doman.FindOrder;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/3/6.
 */

public class FindOrderProcotol {
    public static ArrayList<FindOrder> getOrderInfo(String hsid){
        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetTLD?客户核算ID="+hsid+"&_状态=0").build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                ArrayList<FindOrder> mData=gson.fromJson(string, new TypeToken<List<FindOrder>>(){}.getType());
                return mData;
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
