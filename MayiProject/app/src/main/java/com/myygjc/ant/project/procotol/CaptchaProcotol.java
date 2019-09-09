package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.doman.CaptchaInfoBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/1/18.
 */

public class CaptchaProcotol {

    public static CaptchaInfoBean getCaptcha(String phone, int type){

        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/WebApiJD/api/JD/phoneCaptcha?type="+type+"&PhoneNumber="+phone).build().execute();
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
