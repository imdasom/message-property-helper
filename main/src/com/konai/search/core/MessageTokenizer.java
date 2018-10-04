package com.konai.search.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import com.konai.search.model.Message;
import com.konai.search.model.ResultClass;

public class MessageTokenizer {
	
	public Map<String, Message> getTokenListFromMap(Map<String, String> map) throws IOException {
		Map<String, Message> messageList = new HashMap<String, Message>();
		for (Entry<String, String> entry : map.entrySet()) {
			messageList.put(entry.getKey(), new Message(entry.getValue()));
		}
		return messageList;
	}
	
	public Map<String, String> getMapFromInput(String[] inputMessages) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < inputMessages.length; i++) {
			String key = "INPUTKEY_" + (getZeroPaddingNumber(i+1, 4));
			String value = inputMessages[i];
			map.put(key, value);
		}
		return map;
	}
	
	private String getZeroPaddingNumber(int num, int base) {
		int count = (int) Math.log10(num);
		String paddingNum = "";
		for (int i = 1; i < base - count; i++) {
			paddingNum += "0";
		}
		return paddingNum + num;
	}
	
	public Map<String, String> getMapFromFile(String location) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new FileReader(new File(location)));
		String newLine = null;
		while((newLine = br.readLine()) != null) {
			String[] keyandvalue = newLine.split("=");
			map.put(keyandvalue[0], keyandvalue[1]);
		}
		br.close();
		return map;
	}

	public Map<String, String> getMapFromResource(String location, String bundleName) throws MalformedURLException {
		Map<String, String> map = new HashMap<String, String>();

		File file = new File(location);
		URL[] urls = { file.toURI().toURL() };
		ClassLoader loader = new URLClassLoader(urls);
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, new Locale("ko", "KR"), loader);
		Enumeration<String> enumeration = bundle.getKeys();
		
		while(enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String value = bundle.getString(key);
			map.put(key, value);
		}
		
		return map;
	}
	
//	public void printMessageList(Map<String, Message> map, Map<String, Message> res) {
//		for (Entry<String, Message> e : map.entrySet()) {
//			Message msg = e.getValue();
//			System.out.println("----찾고 싶은 메세지----");
//			System.out.println(e.getKey() + " : " + msg.getOriginMessage());
//			System.out.println("----결과----");
//			for(ResultClass resultClass : msg.resultMap.keySet()) {
//				System.out.println("\t[" + resultClass + "]");
//				for (String key : msg.resultMap.get(resultClass)) {
//					System.out.println("\t" + key + " : " + res.get(key).getOriginMessage());
//				}
//				System.out.println();
//			}
//			System.out.println();
//		}
//	}
}
