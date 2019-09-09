package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.ClassificationProduct;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/10/26.
 */

public class ClassificationProductProcotol {
    public static ClassificationProduct getProduct(String categoryBig, String categorySmall, String brand){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL + "Product/BrandProduct?categoryBig="+categoryBig+"&categorySmall="+categorySmall+"&brand="+brand).build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                ClassificationProduct data=gson.fromJson(string , ClassificationProduct.class);
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
