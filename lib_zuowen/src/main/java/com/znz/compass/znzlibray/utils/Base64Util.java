package com.znz.compass.znzlibray.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Util {

	/**
	 * Base64加密
	 *
	 * @param input
	 * @return
	 */
	public static String encode(String input) {
		return encode(input, "UTF-8");
	}

	public static String encode(byte[] input){
		return Base64.encodeToString(input, Base64.DEFAULT);
	}

	public static String encode(String input, String charsetName) {
		String result = null;
		try {
			result = Base64.encodeToString(input.getBytes(charsetName), Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Base64解密
	 *
	 * @param input
	 * @return
	 */
//	public static String decode(String input) {
//		return decode(input, "UTF-8");
//	}

	public static String decode(String input, String charsetName) {
		try {
			return new String(decode(input.getBytes()), charsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decode(String input){
		return Base64.decode(input, Base64.DEFAULT);
	}

	public static byte[] decode(byte[] input) {
		byte[] arr = null;
		try {
			arr = Base64.decode(input, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
}
