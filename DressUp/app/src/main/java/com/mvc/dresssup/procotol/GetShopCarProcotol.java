package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.GoodsInfo;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/10/30.
 */

public class GetShopCarProcotol {

    public static GoodsInfo getShopCar(){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL + "Cart/CartList").build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();

                GoodsInfo goodsInfo = new Gson().fromJson(string, GoodsInfo.class);
                return goodsInfo;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return null;
        }
    }
}
