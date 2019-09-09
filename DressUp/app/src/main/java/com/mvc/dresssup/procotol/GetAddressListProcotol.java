package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.AddressBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/10/25.
 */

public class GetAddressListProcotol {

    public static ArrayList<AddressBean> getAddressList(){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL+"Account/AddressList").build().execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                ArrayList<AddressBean> data=gson.fromJson(string ,new TypeToken<List<AddressBean>>() {}.getType());
                return data;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
