package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.CaptchaInfoBean;
import com.mvc.dresssup.domain.Verity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/1/18.
 */

public class VerityCaptchaProcotol {
    public static CaptchaInfoBean verityCaptcha(Verity catcha){
        try {
        String s = new Gson().toJson(catcha);
            Response response = OkHttpUtils
                    .postString()
                    .url(Constants.BASE_URL+"Account/verityCaptcha")
                    .content(s)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                CaptchaInfoBean data=gson.fromJson(string,CaptchaInfoBean.class);
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
