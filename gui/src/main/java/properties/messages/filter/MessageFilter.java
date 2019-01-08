package properties.messages.filter;

import string.pattern.common.core.Expression;
import string.pattern.common.vo.KeyValue;
import string.pattern.generate.core.KeyNameRule;
import string.pattern.generate.core.KeyValueGenerator;
import string.pattern.search.vo.ResultClass;
import string.pattern.search.vo.SearchResult;

import java.util.List;
import java.util.function.Function;

public class MessageFilter {

    public static List<KeyValue> getFailureMessage(List<SearchResult> searchResults, ResultClass searchLevel, KeyNameRule keyNameRule) {
        Function<SearchResult, KeyValue> getFailCaseFunction = result -> {
            Expression failureExpressions = new Expression(result.getMessage().getOriginMessage());
            return KeyValueGenerator.generate(failureExpressions, keyNameRule);
        };
        return getFilteredMessages(searchResults, searchLevel, false, getFailCaseFunction);
    }

    private static List<KeyValue> getFilteredMessages(List<SearchResult> searchResults,
                                                      ResultClass searchLevel,
                                                      boolean isSuccess,
                                                      Function<SearchResult,
                                                              KeyValue> getMessagePropertyFunction) {
        SearchResultFilter searchResultFilter = new SearchResultFilter();
        List<SearchResult> successSearchResluts = searchResultFilter.getSearchResult(searchResults, searchLevel, isSuccess);
        return searchResultFilter.getMessageProperties(successSearchResluts, getMessagePropertyFunction);
    }

}
