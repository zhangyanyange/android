package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.doman.AddMaterialCar;
import com.myygjc.ant.project.doman.UserId;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/2/21.
 */

public class AddCarProcotol {

    public static UserId getData(AddMaterialCar catcha){
        try {
            String s = new Gson().toJson(catcha);
            System.out.println("提交订单号"+s);
            Response response = OkHttpUtils
                    .postString()
                    .url("http://123.206.107.160/webapiJD/api/JD/AddCar")
                    .content(s)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                UserId userId1 = gson.fromJson(string, UserId.class);
                return userId1;
            }else{
                return null;
            }
        } catch (Exception e) {
            System.out.println("错误"+e.toString());
            return null;
        }
    }
}
