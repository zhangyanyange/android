package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.AddCar;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/10/30.
 */

public class RemoveShopCarProcotol {

    public static String removeShopCar(ArrayList<AddCar> car){
        try {
            Gson gson = new Gson();
            String s = gson.toJson(car);
            System.out.println(s);
            Response response = OkHttpUtils
                    .postString()
                    .url(Constants.BASE_URL + "Cart/RemoveCart")
                    .content(s)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute();
            if(response.isSuccessful()){

                return response.body().string();
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
