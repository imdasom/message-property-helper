package string.pattern.search.util;

import string.pattern.common.vo.Key;
import string.pattern.search.vo.Message;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

public class KeyValueTokenizer {
	
	public Map<Key, Message> getTokenListFromMap(Map<String, String> map) {
		Map<Key, Message> messageList = new HashMap<Key, Message>();
		for (Entry<String, String> entry : map.entrySet()) {
			messageList.put(new Key(entry.getKey()), new Message(entry.getValue()));
		}
		return messageList;
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

//	public void printMessageList(Map<String, Message> map, Map<String, KeyValue> res) {
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
