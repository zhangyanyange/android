package com.microfeel.meiquetiliao.procotol;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.base.Constants;
import com.microfeel.meiquetiliao.domain.Material;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zy2 on 2017/12/5.
 */

public class GetMaterialProcotol {

    public static Material getMaterial(int typeid){

        try {
            Response response = OkHttpUtils.get().url(Constants.BASE_URL + "Materiel?typeid=" + typeid).build().execute();
            if(response.isSuccessful()){
                String string = response.body().string();
                Gson gson=new Gson();
                Material material = gson.fromJson(string, Material.class);
                return material;
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
