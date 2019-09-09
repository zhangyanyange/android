package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.DeleteOrder;
import com.mvc.dresssup.domain.DeleteOrderResult;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/11/6.
 */

public class DeleteOrderProcotol {
    public static DeleteOrderResult deleteOrder(DeleteOrder deleteOrder){
        try {
            Gson gson = new Gson();
            String s = gson.toJson(deleteOrder);
            Response response = OkHttpUtils
                    .postString()
                    .url(Constants.BASE_URL + "Order/DeleteOrder")
                    .content(s)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                     .execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                DeleteOrderResult result = gson.fromJson(string, DeleteOrderResult.class);
                return  result;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
