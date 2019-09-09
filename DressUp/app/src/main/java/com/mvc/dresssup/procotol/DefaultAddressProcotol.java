package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.AddressBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/11/1.
 */

public class DefaultAddressProcotol {
    public static AddressBean getDefaultAddress(){
        try{
        Response response = OkHttpUtils.get().url(Constants.BASE_URL + "Account/DefaultAddress").build().execute();
        if(response.isSuccessful()){
            String string = response.body().string();
            AddressBean addressBean = new Gson().fromJson(string,AddressBean.class);
            return addressBean;
        }else{
            return null;
        }
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
}
