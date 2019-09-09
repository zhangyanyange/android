package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myygjc.ant.project.doman.Producer;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/3/16.
 */

public class ProducerProcotol {
    public static List<Producer> getProducer(int typeId){
        List<Producer> mData=new ArrayList<>();
        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/WebApiJD/api/JD/GetMaterialType?typeId="+typeId).build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
               mData=gson.fromJson(string,new TypeToken<List<Producer>>(){}.getType());
                return mData;
            }else{
                return null;
            }
        } catch (Exception e) {
            System.out.println("错误"+e.toString());
            return null;
        }
    }
}
