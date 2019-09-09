package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.PopularBrandsBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/10/26.
 */

public class GetPopularBrandProcotol {
    public static ArrayList<PopularBrandsBean> getPopularBrand(String category){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL + "Product/CategoryBrand?category=" + category).build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                ArrayList<PopularBrandsBean> data=gson.fromJson(string ,new TypeToken<List<PopularBrandsBean>>() {}.getType());
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
