package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.OrderDetailBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/11/6.
 */

public class OrderDetailProtocol {
    public  static OrderDetailBean getOrderDetailProtocol(int id){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL +"Order/OrderDetail?Id="+id).build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                OrderDetailBean orderDetailBean = new Gson().fromJson(string, OrderDetailBean.class);

                return orderDetailBean;
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
