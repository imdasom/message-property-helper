package string.pattern.search.core;

import string.pattern.common.core.Expression;
import string.pattern.common.vo.Key;
import string.pattern.search.vo.Message;
import string.pattern.search.vo.SearchResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyValueSearcher {

	private static KeyValueSearcher searcher;

	private KeyValueSearcher() {}

	public static KeyValueSearcher getInstance() {
		if(searcher == null) {
			searcher = new KeyValueSearcher();
		}
		return searcher;
	}

	public List<SearchResult> search(List<Expression> expressionList, Map<Key, Message> resourceTokenList) {
		List<Message> valueList = expressionList.stream()
				.map(value -> new Message(value.getValue()))
				.collect(Collectors.toList());
		KeyValueSearchEngine messageFinderProcessor = new KeyValueSearchEngine();
		List<SearchResult> searchResults = messageFinderProcessor.searchMessageList(valueList, resourceTokenList);
		return searchResults;
	}
}