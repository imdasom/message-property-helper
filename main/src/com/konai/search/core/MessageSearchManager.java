package com.konai.search.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.konai.search.model.Message;
import com.konai.search.model.SearchResult;

public class MessageSearchManager {
	
	public List<SearchResult> findFromResourceFile(String[] inputDatas, String location, String bundleName) throws IOException {
		MessageTokenizer messageTokenizer = new MessageTokenizer();
		Map<String, String> resourceMap = messageTokenizer.getMapFromResource(location, bundleName);
		Map<String, String> inputMap = messageTokenizer.getMapFromInput(inputDatas);
		return find(messageTokenizer, inputMap, resourceMap);
	}
	
	public List<SearchResult> findFromFile(String[] inputDatas, String location) throws IOException {
		MessageTokenizer messageTokenizer = new MessageTokenizer();
		Map<String, String> resourceMap = messageTokenizer.getMapFromFile(location);
		Map<String, String> inputMap = messageTokenizer.getMapFromInput(inputDatas);
		return find(messageTokenizer, inputMap, resourceMap);
	}
	
	private List<SearchResult> find(MessageTokenizer messageTokenizer, Map<String, String> inputMap, Map<String, String> resourceMap) throws IOException {
		Map<String, Message> resourceTokenList = messageTokenizer.getTokenListFromMap(resourceMap);
		Map<String, Message> inputTokenList = messageTokenizer.getTokenListFromMap(inputMap);
		
		MessageSearchEngine messageFinderProcessor = new MessageSearchEngine();
		List<SearchResult> searchResults = messageFinderProcessor.searchMessageList(inputTokenList, resourceTokenList);

//		messageTokenizer.printMessageList(inputTokenList, resourceTokenList);
		return searchResults;
	}
}