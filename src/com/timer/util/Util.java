package com.timer.util;

import java.util.Random;

public class Util {

	public static Random random = new Random();

	public static double parseToDouble(String number) {
		try {
			return Double.parseDouble(number);
		} catch (Exception e) {
			return -1;
		}
	}

	public static int parseToInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (Exception e) {
			return -1;
		}
	}

	public static boolean isParseableToInt(String number) {
		return (Util.parseToInt(number) != -1);
	}

	public static boolean isParseableToDouble(String number) {
		return (Util.parseToDouble(number) != -1);
	}

}
