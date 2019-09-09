package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.doman.CaptchaInfoBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/1/19.
 */

public class LoginCatchaProcotol {
    public static CaptchaInfoBean getCaptcha(String capchacode){

        try {
            Response response = OkHttpUtils
                    .get()
                    .url("http://123.206.107.160/webapiJD/api/JD/login?CapchaCode="+capchacode)
                    .build()
                    .execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                CaptchaInfoBean info=gson.fromJson(string,CaptchaInfoBean.class);
                return info;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
