package com.timer.util;

public class Print {

	public static void warning(String message) {
		println("WARNING: " + message + "\n", false, false);
	}

	public static void normal(String message) {
		println(message + "\n", false, false);
	}

	public static void longResponse(String message) {
		println(message + "\n", true, false);
	}

	public static void error(String message) {
		println(message + "\n", true, true);
	}

	public static void error(String message, boolean longRes) {
		println(message + "\n", longRes, true);
	}

	public static void errorInput(String message) {
		println(message, false, true);
	}

	public static void input(String message) {
		println(message, false, false);
	}

	/*
	 * private static void println(String message, boolean
	 * longRes, boolean isErrorMessage) { if (!isErrorMessage)
	 * System.out.print("(^-^) -"); else
	 * System.out.print("(T-T) -"); if (longRes)
	 * System.out.print("--> " + message); else
	 * System.out.print("> " + message); }
	 */

	private static void println(String message, boolean longRes, boolean isErrorMessage) {
		System.out.print("-");
		if (longRes) System.out.print("--> " + message);
		else System.out.print("> " + message);
	}

	public static void gap() {
		System.out.println();
	}

}
