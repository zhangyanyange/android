package com.microfeel.meiquetiliao.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {



	/**
	 * md5加密  sh-1
	 * @param src
	 * @return
	 */
	public static String encode(String src) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			// 加密
			byte[] digest = md.digest(src.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				int a = b & 0xff;// 取低八位 取正
				String hexString = Integer.toHexString(a);
				if (hexString.length() == 1) {
					hexString = "0" + hexString;
				}
				sb.append(hexString);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}



	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString().toUpperCase();
	}
}
