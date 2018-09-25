package com.konai.search.core;

import java.io.IOException;
import java.util.Map;

import com.konai.search.model.Message;

public class MessageSearchManager {
	
	public Map<String, Message> findFromResourceFile(String[] inputDatas, String location, String bundleName) throws IOException {
		MessageTokenizer messageTokenizer = new MessageTokenizer();
		Map<String, String> resourceMap = messageTokenizer.getMapFromResource(location, bundleName);
		Map<String, String> inputMap = messageTokenizer.getMapFromInput(inputDatas);
		return find(messageTokenizer, inputMap, resourceMap);
	}
	
	public Map<String, Message> findFromFile(String[] inputDatas, String location) throws IOException {
		MessageTokenizer messageTokenizer = new MessageTokenizer();
		Map<String, String> resourceMap = messageTokenizer.getMapFromFile(location);
		Map<String, String> inputMap = messageTokenizer.getMapFromInput(inputDatas);
		return find(messageTokenizer, inputMap, resourceMap);
	}
	
	private Map<String, Message> find(MessageTokenizer messageTokenizer, Map<String, String> inputMap, Map<String, String> resourceMap) throws IOException {
		Map<String, Message> resourceTokenList = messageTokenizer.getTokenListFromMap(resourceMap);
		Map<String, Message> inputTokenList = messageTokenizer.getTokenListFromMap(inputMap);
		
		MessageSearchEngine messageFinderProcessor = new MessageSearchEngine();
		messageFinderProcessor.searchMessageList(inputTokenList, resourceTokenList);

		messageTokenizer.printMessageList(inputTokenList, resourceTokenList);
		return inputTokenList;
	}
}