package com.mvc.microfeel.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvc.microfeel.constans.Constants;
import com.mvc.microfeel.domain.ExInProject;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/9/1.
 */

public class ExIncomeProcotol {
    public static ArrayList<ExInProject> getProjectExIN(String ProjecNO,int accountID){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL+"Project/AccountList?AccountID="+accountID+"&ProjectNO="+ProjecNO).build().execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
              ArrayList<ExInProject> data=gson.fromJson(string ,new TypeToken<List<ExInProject>>() {}.getType());
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
