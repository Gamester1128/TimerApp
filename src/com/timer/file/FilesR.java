package com.timer.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FilesR {

	public static String[] readTextFileWithoutComment(String path) {
		String allLines = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null)
				allLines += commentLess(line) + "/LineSeparator/";
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return allLines.split("/LineSeparator/");
	}

	public static String[] readTextFile(String path) {
		String allLines = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null)
				allLines += line + "/LineSeparator/";
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allLines.split("/LineSeparator/");
	}

	private static String commentLess(String line) {
		if (line.contains("//")) {
			int index = line.indexOf('/');
			line = line.substring(0, index);
			line = line.trim();
		}
		return line;
	}
}
