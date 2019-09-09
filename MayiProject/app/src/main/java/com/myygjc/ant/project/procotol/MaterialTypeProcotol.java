package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myygjc.ant.project.doman.MaterialType;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/1/5.
 */

public class MaterialTypeProcotol {


    public ArrayList<MaterialType> getData(){
        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetMaterialType").build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                    Gson gson=new Gson();
                ArrayList<MaterialType> mData=gson.fromJson(string,new TypeToken<List<MaterialType>>(){}.getType());
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
