package com.microfeel.meiquetiliao.procotol;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.domain.MentionMaterial;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/12/4.
 */

public class GetProjectStatusProcotol {

    public static MentionMaterial getProjectStatus(int status, String id){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL+"Order/"+id+"?state="+status).build().execute();
            if (response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                MentionMaterial mentionMaterial = gson.fromJson(string, MentionMaterial.class);
                return mentionMaterial;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
