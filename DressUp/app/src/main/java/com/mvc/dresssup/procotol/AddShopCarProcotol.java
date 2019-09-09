package com.mvc.dresssup.procotol;

import com.mvc.dresssup.base.Constants;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/10/30.
 */

public class AddShopCarProcotol {

    public static String addshopcar(String s){
        try {
            Response response = OkHttpUtils.postString().url(Constants.BASE_URL + "Cart/AddCart").content(s).mediaType(MediaType.parse("application/json; charset=utf-8")).build().execute();
            if(response.isSuccessful()){
                return response.body().string();
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
