package com.timer.file.oto;

import java.util.HashMap;
import java.util.Set;

import com.timer.file.FilesR;

public class OTOObject {

	private HashMap<String, String> hash = new HashMap<String, String>();

	public OTOObject(String path) {
		fillHashMap(FilesR.readTextFileWithoutComment(path));
	}

	private void fillHashMap(String[] file) {
		for (int i = 0; i < file.length; i++) {
			String[] line = file[i].split("=");
			//handle more than 1 equals sign
			if (line.length != 2) {
				try {
					throw new Exception("Line index " + i + " contains more than 1 ='s sign | length = " + line.length);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}
			hash.put(line[0], line[1]);
		}
	}

	public String get(String key) {
		return hash.get(key);
	}

	public Set<String> getAllKeys() {
		return hash.keySet();
	}

	public void dispose() {
		hash = null;
	}

}
