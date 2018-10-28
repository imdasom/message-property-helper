package properties.messages.filter;

import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchResultFilter {

    public List<SearchResult> getSearchResult(List<SearchResult> searchResults, ResultClass filterLevel, boolean isSuccess) {
        List<SearchResult> successSearchResult = new ArrayList<>();
        for (SearchResult searchResult : searchResults) {
            if(isSuccessCase(searchResult, filterLevel) == isSuccess) {
                successSearchResult.add(searchResult);
            }
        }
        return successSearchResult;
    }

    protected boolean isSuccessCase(SearchResult searchResult, ResultClass filterLevel) {
        boolean isSuccess = true;
        if(searchResult.isResultMapEmpty()) {
            isSuccess = false;
        } else {
            Optional<Key> key = searchResult.getKeyByLevel(filterLevel);
            if(!key.isPresent()) {
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    public List<MessageProperty> getMessageProperties(List<SearchResult> searchResults, Function<SearchResult, MessageProperty> get) {
        List<MessageProperty> messageProperties = searchResults.stream()
                .map(get)
                .collect(Collectors.toList());
        return messageProperties;
    }
}
