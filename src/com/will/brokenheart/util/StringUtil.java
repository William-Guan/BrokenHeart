package com.will.brokenheart.util;

public class StringUtil {

	public static String autoPushZero(int num, String pattern) {
		if (pattern == null) {
			pattern = "000";
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
		return df.format(num);
	}
}
