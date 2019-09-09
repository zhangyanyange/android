package com.myygjc.ant.project.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zy2 on 2017/2/24.
 */

public class DesUtils {
    private static byte[]iv="VavicApp".getBytes();

    public static  String encryptDes(String encryptString) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec("VavicApp".getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            return Base64.encode(encryptedData);
        }catch (Exception e){

        }
        return "";
    }
}
