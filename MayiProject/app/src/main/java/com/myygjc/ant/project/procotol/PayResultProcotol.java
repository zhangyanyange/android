package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.myygjc.ant.project.doman.UserId;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/3/3.
 */

public class PayResultProcotol {
    public static UserId getResult(String HSID, String ywdjh) {
        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/WebApiJD/api/JD/GetPayStatus?hsid="+HSID+"&ywdjh="+ywdjh).build().execute();
            if (response.isSuccessful()) {
                String string = response.body().string();
                Gson gson = new Gson();
                UserId userId = gson.fromJson(string, UserId.class);
                return userId;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
