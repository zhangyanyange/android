package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.AddressBean;
import com.mvc.dresssup.domain.AddressStateBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/10/25.
 */

public class AddAddressProcotol {
    public static AddressStateBean addAddress(AddressBean bean){
        try {
            String s = new Gson().toJson(bean);
            Response response = OkHttpUtils
                                .postString()
                                .url(Constants.BASE_URL + "Account/AddAddress")
                                .content(s)
                                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                .build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
               AddressStateBean data=gson.fromJson(string,AddressStateBean.class);
                return data;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
