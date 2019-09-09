package com.microfeel.meiquetiliao.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zy2 on 2017/2/24.
 */

public class DesUtils {
    private static byte[]iv="VavicApp".getBytes();

    public static String encryptDes(String encryptString) {
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

    public static String decryptDES(String decryptString, String decryptKey)
            throws Exception {
        byte[] byteMi = new Base64().decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }
}
