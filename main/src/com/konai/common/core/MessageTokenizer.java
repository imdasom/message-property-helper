package com.konai.common.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import com.konai.common.util.StringUtils;
import com.konai.common.vo.Key;
import com.konai.search.vo.Message;

public class MessageTokenizer {
	
	public Map<Key, Message> getTokenListFromMap(Map<String, String> map) throws IOException {
		Map<Key, Message> messageList = new HashMap<Key, Message>();
		for (Entry<String, String> entry : map.entrySet()) {
			messageList.put(new Key(entry.getKey()), new Message(entry.getValue()));
		}
		return messageList;
	}

	public List<Message> getMessageList(List<String> list) {
		List<Message> messages = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			messages.add(new Message(list.get(i)));
		}
		return messages;
	}
	
	public Map<String, String> getMapFromInput(String[] inputMessages) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < inputMessages.length; i++) {
			String key = "INPUTKEY_" + (StringUtils.getZeroPaddingNumber(i+1, 4));
			String value = inputMessages[i];
			map.put(key, value);
		}
		return map;
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

	public Map<String, String> getMapFromResource(ResourceBundle bundle) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enumeration = bundle.getKeys();
		while(enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String value = bundle.getString(key);
			map.put(key, value);
		}
		return map;
	}
	
//	public void printMessageList(Map<String, Message> map, Map<String, MessageProperty> res) {
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
