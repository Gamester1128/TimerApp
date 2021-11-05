package com.timer.util;

import java.util.Scanner;

public class Input {

	private static Scanner scanner = new Scanner(System.in);

	public static double Double(String message) {
		return Double(message, message);
	}

	public static double Double(String message, String error) {
		Print.input(message);
		while (true) {
			try {
				return scanner.nextDouble();
			} catch (Exception e) {
				Print.errorInput(error);
			}
		}
	}

	public static String string(String message) {
		return string(message, message);
	}

	public static String string(String message, String error) {
		Print.input(message);
		return scanner.nextLine();
	}

	public static int integer(String message) {
		return integer(message, message);
	}

	public static int integer(String message, String error) {
		Print.input(message);
		while (true) {
			try {
				return scanner.nextInt();
			} catch (Exception e) {
				Print.errorInput(error);
			}
		}
	}

}
