package com.dahuatech.app.common;

import android.annotation.SuppressLint;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @ClassName DESUtils
 * @Description DES加密/解密工具
 * @author 21291
 * @date 2014年8月8日 上午9:46:28
 */
public class DESUtils {
	
	//默认的秘钥key
	public static final String DEFAULT_KEY = "02adfd5a";

	/**
	 * 解密
	 * @param message 加密后的内容
	 * @param key 秘钥
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt(String message, String key) throws Exception {
		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	/**
	 * 加密
	 * @param message 需要加密的数据
	 * @param key 秘钥
	 * @return 加密后的字符数组
	 * @throws Exception
	 */
	@SuppressLint("TrulyRandom")
	public static byte[] encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message.getBytes("UTF-8"));
	}

	/**
	 * 把字符串转换成字符数组
	 * @param ss 字符串
	 * @return 字符数组
	 */
	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	/**
	 * 加密
	 * @param message 加密的内容
	 * @param key 秘钥
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	@SuppressLint("DefaultLocale")
	public static String encrypt2(String message, String key) throws Exception {
		String jiami = java.net.URLEncoder.encode(message, "utf-8")
				.toLowerCase();
		String a = toHexString(encrypt(jiami, key)).toUpperCase();
		return a;
	}

	/**
	 * 解密
	 * @param message 加密的字符串
	 * @param key 秘钥
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt2(String message, String key) throws Exception {
		return java.net.URLDecoder.decode(decrypt(message, key),"utf-8");
	}
	/**
	 * 把字符数组转换成字符串
	 * @param 字符数组
	 * @return 字符串
	 */
	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}
}