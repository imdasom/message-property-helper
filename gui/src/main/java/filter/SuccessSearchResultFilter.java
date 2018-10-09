package filter;

import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;
import com.konai.common.vo.Value;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SuccessSearchResultFilter extends SearchResultFilter {

    public List<MessageProperty> getMessageProperties(List<SearchResult> successSearchResult, ResultClass filterLevel){
        List<MessageProperty> messageProperties = successSearchResult.stream()
                .map(result -> {
                    Key key = result.getKeyByLevel(filterLevel).get();
                    Value value = new Value(result.getMessage().getOriginMessage());
                    return new MessageProperty(key, value);
                })
                .collect(Collectors.toList());
        return messageProperties;
    }

    public List<SearchResult> getSuccessSearchResult(List<SearchResult> searchResults, ResultClass filterLevel) {
        List<SearchResult> successSearchResult = new ArrayList<>();
        for (SearchResult searchResult : searchResults) {
            if(!isFailCase(searchResult, filterLevel)) {
                successSearchResult.add(searchResult);
            }
        }
        return successSearchResult;
    }
}
