package org.topicquests.support.config;

import org.topicquests.support.util.ConfigurationHelper;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.util.*;

public class ConfigPullParser {
	private Map<String, Object> properties = new Hashtable();

	public ConfigPullParser(String configFilePath) {
		try {
			String path = ConfigurationHelper.findPath(configFilePath);
			File f = new File(path);
			FileInputStream fis = new FileInputStream(f);
			this.parse(fis);
		} catch (Exception var5) {
			throw new RuntimeException(var5.getMessage());
		}
	}

	public ConfigPullParser(InputStream inStream) {
		try {
			this.parse(inStream);
		} catch (Exception var3) {
			throw new RuntimeException(var3.getMessage());
		}
	}

	public Map<String, Object> getProperties() {
		return this.properties;
	}

	void parse(InputStream is) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser xpp = factory.newPullParser();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			xpp.setInput(in);
			String temp = null;
			String text = null;
			String name = null;
			String listName = null;
			String stringMapName = null;
			String value = null;
			HashMap attributes = null;
			ArrayList<List> theList = null;
			Map<String, String> stringMap = null;
			boolean isList = false;
			boolean isStringMap = false;
			int eventType = xpp.getEventType();

			for (boolean isStop = false; !isStop && eventType != 1; eventType = xpp.next()) {
				temp = xpp.getName();
				attributes = this.getAttributes(xpp);
				if (attributes != null) {
					name = (String) attributes.get("name");
					value = (String) attributes.get("value");
				} else {
					name = null;
					value = null;
				}

				if (eventType == 0) {
					System.out.println("Start document");
				} else if (eventType == 1) {
					System.out.println("End document");
				} else if (eventType == 2) {
					System.out.println("Start tag " + temp);
					if (temp.equalsIgnoreCase("parameter")) {
						if (isList) {
							ArrayList<String> list = new ArrayList();
							list.add(name);
							list.add(value);
							theList.add(list);
						} else if (isStringMap) {
							stringMap.put(name, value);
						} else {
							this.properties.put(name, value);
						}
					} else if (temp.equalsIgnoreCase("list")) {
						listName = name;
						theList = new ArrayList();
						isList = true;
					} else if (temp.equalsIgnoreCase("stringMap")) {
						stringMapName = name;
						stringMap = new HashMap();
						isStringMap = true;
					}
				} else if (eventType == 3) {
					System.out.println("End tag " + temp + " // " + text);
					if (!temp.equalsIgnoreCase("parameter")) {
						if (temp.equalsIgnoreCase("list")) {
							this.properties.put(listName, theList);
							listName = null;
							theList = null;
							isList = false;
						} else if (temp.equalsIgnoreCase("stringMap")) {
							this.properties.put(stringMapName, stringMap);
							stringMapName = null;
							stringMap = null;
							isStringMap = false;
						}
					}
				} else if (eventType == 4) {
					text = xpp.getText().trim();
				} else if (eventType == 5) {
					text = xpp.getText().trim();
				}
			}
		} catch (XmlPullParserException var19) {
			System.out.println("ServerPullParser parser failed " + var19.getMessage());
		} catch (IOException var20) {
			System.out.println("ServerPullParser parser io failure " + var20.getMessage());
		}

	}

	HashMap getAttributes(XmlPullParser p) {
		HashMap<String, String> result = null;
		int count = p.getAttributeCount();
		if (count > 0) {
			result = new HashMap();
			String name = null;

			for (int i = 0; i < count; ++i) {
				name = p.getAttributeName(i);
				result.put(name, p.getAttributeValue(i));
			}
		}

		return result;
	}
}
