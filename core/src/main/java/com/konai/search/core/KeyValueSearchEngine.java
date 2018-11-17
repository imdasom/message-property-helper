package com.konai.search.core;

import com.konai.common.vo.Key;
import com.konai.search.vo.Message;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MessageSearchEngine {

	public List<SearchResult> searchMessageList(List<Message> inputList, Map<Key, Message> resourceList) {
	    List<SearchResult> searchResults = new ArrayList<>();
		for (Message message : inputList) {
            SearchResult searchResult = searchMessage(message, resourceList);
            searchResults.add(searchResult);
		}
		return searchResults;
	}
	
	public SearchResult searchMessage(Message input, Map<Key, Message> resourceList) {
        SearchResult searchResult = new SearchResult(input);
        for (Entry<Key, Message> resElement : resourceList.entrySet()) {
			Key resourceKey = resElement.getKey();
			Message resource = resElement.getValue();
			// TODO if-else -> strategy pattern 적용하기
			if(input.value.length() <= resource.value.length()) {
				if(MessageComparator.compareValue(resource.value, input.value)) {
					if(MessageComparator.compareTotalToken(input.tokens, resource.tokens)) {
						searchResult.putKeyResultMap(ResultClass.TotalEqual, resourceKey);
					} else {
						searchResult.putKeyResultMap(ResultClass.TotalSimilar, resourceKey);
					}
				}
			} else {
				if(MessageComparator.compareValue(input.value, resource.value)) {
					if(MessageComparator.comparePartialToken(resource.tokens, input.tokens)) {
						searchResult.putKeyResultMap(ResultClass.PartialEquals, resourceKey);
					} else {
						searchResult.putKeyResultMap(ResultClass.PartialSimilar, resourceKey);
					}
				}
			}
		}
		return searchResult;
	}
}
