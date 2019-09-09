package com.mvc.microfeel.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvc.microfeel.constans.Constants;
import com.mvc.microfeel.domain.ProjectList;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/8/31.
 */

public class GetProjectListProcotol {
    public static ArrayList<ProjectList> getProjectList(int projectType){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL+"Project?ProjectValue="+projectType).build().execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                ArrayList<ProjectList> data=gson.fromJson(string ,new TypeToken<List<ProjectList>>() {}.getType());
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
