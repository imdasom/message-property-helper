package com.konai.search.core;

import com.konai.common.vo.Key;
import com.konai.search.vo.Message;
import com.konai.search.vo.SearchResult;

import java.util.List;
import java.util.Map;

public class MessagePropertySearcher {

	public List<SearchResult> search(List<Message> valueList, Map<Key, Message> resourceTokenList) {
		MessageSearchEngine messageFinderProcessor = new MessageSearchEngine();
		List<SearchResult> searchResults = messageFinderProcessor.searchMessageList(valueList, resourceTokenList);
		return searchResults;
	}
}