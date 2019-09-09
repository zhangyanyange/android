package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.OrderAllBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/11/3.
 */

public class OrderListProcotol {
    public static ArrayList<OrderAllBean> getOrderList(String status){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL + "Order/OrderList?status="+status).build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson = new Gson();
                ArrayList<OrderAllBean>data=gson.fromJson(string ,new TypeToken<List<OrderAllBean>>() {}.getType());
                return data;
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
