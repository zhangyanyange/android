package com.myygjc.ant.project.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myygjc.ant.project.doman.RecommendBuy;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/2/22.
 */

public class HotSearchProcotol {

    public List<RecommendBuy> getData(String userId){
        try {
            Response response = OkHttpUtils.get().url("http://123.206.107.160/webapiJD/api/JD/GetRecommendMaterial?客户核算ID="+userId).build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                List<RecommendBuy>  mData=gson.fromJson(string,new TypeToken<List<RecommendBuy>>(){}.getType());
                return mData;
            }else{
                return null;
            }
        } catch (Exception e) {
            System.out.println("错误"+e.toString());
            return null;
        }
    }
}
