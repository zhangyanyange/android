package com.microfeel.meiquetiliao.procotol;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.domain.Shipped;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/12/7.
 */

public class GetShippedProcotol {

    public static Shipped getShipped(String location){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL + "order/GetfinishOrder?address=" + location).build().execute();

            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson = new Gson();
                Shipped shipped = gson.fromJson(string, Shipped.class);
               return shipped;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
