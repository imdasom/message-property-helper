package com.konai.search.core;

import com.konai.search.model.Message;
import com.konai.search.model.ResultClass;
import com.konai.search.model.SearchResult;
import com.konai.search.model.SearchResultType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MessageSearchEngine {

	public List<SearchResult> searchMessageList(Map<String, Message> inputList, Map<String, Message> resourceList) {
	    List<SearchResult> searchResults = new ArrayList<>();
		for (Entry<String, Message> e : inputList.entrySet()) {
            SearchResult searchResult = searchMessage(e, resourceList);
            searchResult.setResultType(getSearchResultType(searchResult));
            searchResults.add(searchResult);
		}
		return searchResults;
	}
	
	private SearchResult searchMessage(Entry<String, Message> inputElement, Map<String, Message> resourceList) {
		Message input = inputElement.getValue();
        SearchResult searchResult = new SearchResult(input);
        for (Entry<String, Message> resElement : resourceList.entrySet()) {
			String resourceKey = resElement.getKey();
			Message resource = resElement.getValue();
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

	private SearchResultType getSearchResultType(SearchResult searchResult) {
        if(searchResult.getResultMap().size() > 0 ) {
            return SearchResultType.Success;
        } else {
            return SearchResultType.Failuer;
        }
    }
}
