package com.mvc.microfeel.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvc.microfeel.constans.Constants;
import com.mvc.microfeel.domain.ExInAllProject;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/9/1.
 */

public class ExIncomeAllProcotol {
    public static ArrayList<ExInAllProject> getAllProjectExIN(String ProjecNO){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL+"Project/AccountAmount?ProjectNO="+ProjecNO).build().execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                System.out.println(string);
              ArrayList<ExInAllProject> data=gson.fromJson(string ,new TypeToken<List<ExInAllProject>>() {}.getType());
                System.out.println(string);
                return data;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return null;
        }
    }
}
