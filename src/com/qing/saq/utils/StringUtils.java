package com.qing.saq.utils;

import java.util.UUID;

public class StringUtils {

	public static boolean isEmpty(String str) {
		return str == null ? true : "".equals(str);
	}
	
	public static String ifNull(String str, String defaultStr) {
		return str == null ? (defaultStr == null ? "" : defaultStr) : str;
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
