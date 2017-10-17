package com.gls.search.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtility {
	/**
	 * 判断字符串是否为null或者是空串""
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.length() == 0;
	}
	public static boolean equals(String str1, String str2) {
		if (str1 == str2) {
			return true;
		}
		return str1 != null && str1.equals(str2);
	}

	public static int getDisplaySize(String str) {
		int size = str.length();
		for (char c : str.toCharArray()) {
			if (isChinese(c))
				++size;
		}
		return size;
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	public static boolean hasChinese(String str) {
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static String padLeft(String str, int minLength) {
		return padLeft(str, minLength, ' ');

	}

	public static String padLeft(String str, int minLength, char pad) {
		if (str.length() >= minLength) {
			return str;
		}
		return newString(pad, minLength - str.length()) + str;
	}

	public static String padRight(String str, int minLength) {
		return padRight(str, minLength, ' ');
	}

	public static String padRight(String str, int minLength, char pad) {
		if (str.length() >= minLength) {
			return str;
		}
		return str + newString(pad, minLength - str.length());
	}

	public static String newString(char c, int length) {
		char[] array = new char[length];
		for (int i = 0; i < length; ++i) {
			array[i] = c;
		}
		return new String(array);
	}

	/**
	 * 将字符串进行转换替换里面的引号和双引号
	 * 
	 * @param str
	 * @return
	 */
	public static String getJsString(String str) {
		if (!isNullOrEmpty(str))
			return str.replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"");
		return EMPTY;
	}

	public static String EMPTY = "";

	/**
	 * descriptiton 特殊字符处理
	 * 
	 * @param str
	 * @return
	 */
	public static String string2Json(String str) {
		if (isNullOrEmpty(str))
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {

			char c = str.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\'':
				sb.append("\\\'");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String decodeSearchContent(String search) {
		if (!StringUtility.isNullOrEmpty(search)) {
			search = search.replace("_", "\\");
			search = StringUtility.unicodeToString(search);
			if (search.startsWith("\\")) {
				search = search.replaceFirst("\\\\", "");
			}
			search = search.trim();
		}
		return search;
	}

	@SuppressWarnings("rawtypes")
	public static String join(Iterable lines, String join) {
		if (lines == null) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (Object str : lines) {
			if (str != null) {
				result.append(str);
				result.append(join);
			}
		}
		if (result.length() > 0) {
			result.setLength(result.length() - join.length());
		}
		return result.toString();
	}

	public static String join(Object[] lines, String join) {
		if (lines == null) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (Object str : lines) {
			if (str != null) {
				result.append(str);
				result.append(join);
			}
		}
		if (result.length() > 0) {
			result.setLength(result.length() - join.length());
		}
		return result.toString();
	}

	public static String unicodeToString(String str) {

		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}
}