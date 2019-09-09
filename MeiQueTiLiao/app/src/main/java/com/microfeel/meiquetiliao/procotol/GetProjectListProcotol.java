package com.microfeel.meiquetiliao.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.domain.GetProjectList;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/9/1.
 */

public class GetProjectListProcotol {
    public static ArrayList<GetProjectList> getProjectList(){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL+"Project").build().execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                ArrayList<GetProjectList> data=gson.fromJson(string ,new TypeToken<List<GetProjectList>>(){}.getType());
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
