package org.topicquests.util;

import java.io.File;

public class ConfigurationHelper {
	public ConfigurationHelper() {
	}

	public static String findPath(String inFilePath) {
		File f = new File(inFilePath);
		if (f.exists()) {
			return inFilePath;
		} else {
			String result = "config/" + inFilePath;
			f = new File(result);
			if (f.exists()) {
				return result;
			} else {
				result = "conf/" + inFilePath;
				f = new File(result);
				if (f.exists()) {
					return result;
				} else {
					result = "cfg/" + inFilePath;
					f = new File(result);
					if (f.exists()) {
						return result;
					} else {
						throw new RuntimeException("File " + result + " not found");
					}
				}
			}
		}
	}
}
