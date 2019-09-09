package com.mvc.dresssup.procotol;

import com.google.gson.Gson;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.ProductDetail;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/10/30.
 */

public class ProductDetailProcotol {

    public static ProductDetail getProductDetail(String productId){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL + "Product/Product?id=" + productId).build().execute();

            if(response.isSuccessful()){
                String string = response.body().string();
              //  System.out.println(string);
                Gson gson = new Gson();
                ProductDetail productDetail = gson.fromJson(string, ProductDetail.class);
                return productDetail;
            }else{
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
