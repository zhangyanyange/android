package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.doman.UserId;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/1/22.
 */

public class UserIdProcotol {

    public static UserId getUserId(final String phoneNumber){
                try {
                    Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/HSID?phoneNumber=" + phoneNumber).build().execute();
                    if(response.isSuccessful()) {
                        String string = response.body().string();
                        Gson gson = new Gson();
                        return gson.fromJson(string, UserId.class);
                    }else{
                        return null;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
    }
}
