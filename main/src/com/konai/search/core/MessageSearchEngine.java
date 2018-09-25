package com.konai.search.core;

import com.konai.search.model.Message;
import com.konai.search.model.ResultClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MessageSearchEngine {

	public void searchMessageList(Map<String, Message> inputList, Map<String, Message> resourceList) {
		for (Entry<String, Message> e : inputList.entrySet()) {
			searchMessage(e, resourceList);
		}
	}
	
	private void searchMessage(Entry<String, Message> inputElement, Map<String, Message> resourceList) {
		Message input = inputElement.getValue();
		for (Entry<String, Message> resElement : resourceList.entrySet()) {
			String resourceKey = resElement.getKey();
			Message resource = resElement.getValue();
			if(input.value.length() <= resource.value.length()) {
				if(MessageComparator.compareValue(resource.value, input.value)) {
					if(MessageComparator.compareTotalToken(input.tokens, resource.tokens)) {
						putKeyResultMap(input.resultMap, ResultClass.TotalEqual, resourceKey);
					} else {
						putKeyResultMap(input.resultMap, ResultClass.TotalSimilar, resourceKey);
					}
				}
			} else {
				if(MessageComparator.compareValue(input.value, resource.value)) {
					if(MessageComparator.comparePartialToken(resource.tokens, input.tokens)) {
						putKeyResultMap(input.resultMap, ResultClass.PartialEquals, resourceKey);
					} else {
						putKeyResultMap(input.resultMap, ResultClass.PartialSimilar, resourceKey);
					}
				}
			}
		}
	}
	
	private void putKeyResultMap(Map<ResultClass, List<String>> map, ResultClass resultClass, String key) {
		List<String> keyList = map.get(resultClass);
		if(keyList == null) {
			keyList = new ArrayList<String>();
		}
		keyList.add(key);
		map.put(resultClass, keyList);
	}

}
