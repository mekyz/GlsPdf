package com.gls.util;

public class StrUtil {

	public static boolean isEmpty(String str) {
		if (null == str || str.equals("")) {
			return false;
		}
		return true;
	}
}
