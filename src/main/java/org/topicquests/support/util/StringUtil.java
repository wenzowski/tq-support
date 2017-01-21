package org.topicquests.support.util;

public class StringUtil {
	public StringUtil() {
	}

	public static String stripNonCharCodepoints(String input) {
		StringBuilder retval = new StringBuilder();

		for (int i = 0; i < input.length(); ++i) {
			char ch = input.charAt(i);
			if (ch % 65536 != '\uffff' && ch % 65536 != '\ufffe' && (ch <= '\ufdd0' || ch >= '\ufdef') && (ch > 31 || ch == 9 || ch == 10 || ch == 13)) {
				retval.append(ch);
			}
		}

		return retval.toString();
	}
}
