package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.doman.CaptchaInfoBean;
import com.myygjc.ant.project.doman.CatchaVerity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by zy2 on 2017/1/18.
 */

public class VerityCaptchaProcotol {

    public static CaptchaInfoBean verityCaptcha(CatchaVerity catcha){

        try {
            String s = new Gson().toJson(catcha);
            Response response = OkHttpUtils
                    .postString()
                    .url("http://123.206.107.160/webapiJD/api/JD/verityCaptcha")
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
