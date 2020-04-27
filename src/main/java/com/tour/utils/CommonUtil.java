package com.tour.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	public static boolean isEmailValid(String email) {
		Pattern emailPattern = Pattern.compile(
				"^[_a-zA-Z0-9!#$%&'*+-/=?^_`{|}~;]+(\\.[_a-zA-Z0-9!#$%&'*+-/=?^_`{|}~;]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$");
		Matcher m = emailPattern.matcher(email);
		return m.matches();
	}

	public static boolean isNameValid(String name) {
		Pattern namePattern = Pattern.compile("[A-Za-z]+([ ][A-Za-z]+)*");
		Matcher m = namePattern.matcher(name);
		return m.matches();
	}

	public static String toUppercase(String string) {
		String[] words = string.split("\\s");
		StringBuilder sb = new StringBuilder();
		for (String s : words) {
			if (!s.equals("")) {
				sb.append(Character.toUpperCase(s.charAt(0)));
				sb.append(s.substring(1));
			}
			sb.append(" ");
		}
		return sb.toString().trim();
	}

}
