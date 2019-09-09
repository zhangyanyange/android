package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/11/2.
 */

public class GetOrderListProcotol {

    public static String getOrderList(){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL + "Order/OrderList").build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
//                ArrayList<PopularBrandsBean> data=gson.fromJson(string ,new TypeToken<List<PopularBrandsBean>>() {}.getType());
                return string;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
