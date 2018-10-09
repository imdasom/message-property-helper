import com.konai.common.core.Expression;
import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;
import com.konai.common.vo.Value;
import com.konai.generate.core.KeyNameRule;
import com.konai.generate.core.MessagePropertyGenerator;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import com.konai.search.vo.SearchResultMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchResultFilter {

    public List<MessageProperty> getMessagePropertyByClassLevel(List<SearchResult> searchResults, ResultClass filterLevel, KeyNameRule keyNameRule) {
        List<SearchResult> failureSearchReuslts = getFailureSearchResult(searchResults, filterLevel);
        List<SearchResult> successSearchResult = getSuccessSearchResult(searchResults, filterLevel);

        List<MessageProperty> newMessageProperties = getFailureMessageProperties(keyNameRule, failureSearchReuslts);
        List<MessageProperty> oldMessageProperties = successSearchResult.stream()
                .map(result -> {
                    Key key = result.getKeyByLevel(filterLevel).get();
                    Value value = new Value(result.getMessage().getOriginMessage());
                    return new MessageProperty(key, value);
                })
                .collect(Collectors.toList());

        oldMessageProperties.addAll(newMessageProperties);
        return oldMessageProperties;
    }

    private List<MessageProperty> getFailureMessageProperties(KeyNameRule keyNameRule, List<SearchResult> failureSearchReuslts) {
        MessagePropertyGenerator generator = new MessagePropertyGenerator();
        List<Expression> failureExpressions = failureSearchReuslts.stream()
                .map(result -> new Expression(result.getMessage().getOriginMessage()))
                .collect(Collectors.toList());
        return generator.generate(failureExpressions, keyNameRule);
    }

    private List<SearchResult> getFailureSearchResult(List<SearchResult> searchResults, ResultClass filterLevel) {
        List<SearchResult> failureSearchResults = new ArrayList<>();
        for (SearchResult searchResult : searchResults) {
            if(isFailCase(searchResult, filterLevel)) {
                failureSearchResults.add(searchResult);
            }
        }
        return failureSearchResults;
    }

    private List<SearchResult> getSuccessSearchResult(List<SearchResult> searchResults, ResultClass filterLevel) {
        List<SearchResult> successSearchResult = new ArrayList<>();
        for (SearchResult searchResult : searchResults) {
            if(!isFailCase(searchResult, filterLevel)) {
                successSearchResult.add(searchResult);
            }
        }
        return successSearchResult;
    }

    private boolean isFailCase(SearchResult searchResult, ResultClass filterLevel) {
        boolean isFailCase = false;
        if(searchResult.isResultMapEmpty()) {
            isFailCase = true;
        } else {
            Optional<Key> key = searchResult.getKeyByLevel(filterLevel);
            if(key == null) {
                isFailCase = true;
            }
        }
        return isFailCase;
    }
}
