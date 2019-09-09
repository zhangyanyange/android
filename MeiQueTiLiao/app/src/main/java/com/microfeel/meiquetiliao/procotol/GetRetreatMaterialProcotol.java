package com.microfeel.meiquetiliao.procotol;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.domain.RetreactMaterial;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/12/15.
 */

public class GetRetreatMaterialProcotol {

    public static RetreactMaterial getMaterial(String location){
        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL + "Order/GetFinishAll?address="+location).build().execute();
            if(response.isSuccessful()){
                RetreactMaterial retreactMaterial = new Gson().fromJson(response.body().string(), RetreactMaterial.class);
                return retreactMaterial;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
