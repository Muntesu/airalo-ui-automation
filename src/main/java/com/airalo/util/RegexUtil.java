package com.airalo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public static long parseNumber(String text) {
		return Long.parseLong(parseRegex(text, "\\d+"));
	}

	public static double parsePrice(String text) {
		return Double.parseDouble(parseRegex(text, "\\d+\\.\\d\\d"));
	}

	public static String parseRegex(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.group(0);
		}
		throw new RuntimeException("Couldn't find the requested value in: " + text);
	}

}
