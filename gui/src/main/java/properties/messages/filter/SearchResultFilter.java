package properties.messages.filter;

import string.pattern.common.vo.Key;
import string.pattern.common.vo.KeyValue;
import string.pattern.search.vo.ResultClass;
import string.pattern.search.vo.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchResultFilter {

    public List<SearchResult> getSearchResult(List<SearchResult> searchResults, ResultClass filterLevel, boolean isSuccess) {
        List<SearchResult> successSearchResult = new ArrayList<>();
        for (SearchResult searchResult : searchResults) {
            if (isSuccessCase(searchResult, filterLevel) == isSuccess) {
                successSearchResult.add(searchResult);
            }
        }
        return successSearchResult;
    }

    protected boolean isSuccessCase(SearchResult searchResult, ResultClass filterLevel) {
        boolean isSuccess = true;
        if (searchResult.isResultMapEmpty()) {
            isSuccess = false;
        } else {
            Optional<Key> key = searchResult.getKeyByLevel(filterLevel);
            if (!key.isPresent()) {
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    public List<KeyValue> getMessageProperties(List<SearchResult> searchResults, Function<SearchResult, KeyValue> get) {
        List<KeyValue> messageProperties = searchResults.stream()
                .map(get)
                .collect(Collectors.toList());
        return messageProperties;
    }
}
