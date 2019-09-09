package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myygjc.ant.project.doman.DelectMaterialCar;
import com.myygjc.ant.project.doman.GetMaterialCount;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/2/27.
 */

public class GetMaterialInventoryProcotol {
    public static ArrayList<GetMaterialCount> getSurplus(DelectMaterialCar delectMaterialCar) {
        try {
            String s = new Gson().toJson(delectMaterialCar);
            Response response = OkHttpUtils
                    .postString()
                    .url("http://123.206.107.160/webapiJD/api/JD/GetMaterialInventory")
                    .content(s)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute();
            if (response.isSuccessful()) {
                String string = response.body().string();
                Gson gson = new Gson();
                ArrayList<GetMaterialCount>data = gson.fromJson(string, new TypeToken<List<GetMaterialCount>>() {}.getType());
                return data;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("错误" + e.toString());
            return null;
        }
    }
}
