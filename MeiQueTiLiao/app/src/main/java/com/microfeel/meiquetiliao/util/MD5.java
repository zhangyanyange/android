package com.microfeel.meiquetiliao.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zy2 on 2017/1/18.
 */

public class MD5 {

    // 进行md5的加密运算
    public static String md5(String data) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(data.getBytes());

            StringBuffer sb=new StringBuffer();
            for (byte b:bytes){
                int num=b&0xff;
                String hex = Integer.toHexString(num);
                sb.append(hex);
            }

            return sb.toString().replace("_","").toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
