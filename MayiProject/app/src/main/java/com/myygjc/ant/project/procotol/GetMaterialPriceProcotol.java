package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.doman.MoneyHas;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/2/24.
 */

public class GetMaterialPriceProcotol {
    public static MoneyHas delectData(String HSID,int wlID) {
        try {
                Response response = OkHttpUtils.get().url("http://123.206.107.160/WebApiJD/api/JD/GetMaterialPrice?核算Id="+HSID+"&wlID="+wlID).build().execute();
                if (response.isSuccessful()) {

                    String string = response.body().string();
                    Gson gson = new Gson();
                    MoneyHas data = gson.fromJson(string, MoneyHas.class);
                    return data;
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

    }
}
