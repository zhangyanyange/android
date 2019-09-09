package com.mvc.microfeel.protocol;

import com.google.gson.Gson;
import com.mvc.microfeel.constans.Constants;
import com.mvc.microfeel.domain.CaptchaDataBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/8/29.
 */

public class LoginProcotol {
    public static CaptchaDataBean login(String id){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL+"Account/login?id="+ id).build().execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
            CaptchaDataBean data=gson.fromJson(string,CaptchaDataBean.class);
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
