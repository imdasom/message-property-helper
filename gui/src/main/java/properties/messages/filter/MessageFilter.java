package properties.messages.filter;

import com.konai.common.core.Expression;
import com.konai.common.vo.KeyValue;
import com.konai.generate.core.KeyNameRule;
import com.konai.generate.core.KeyValueGenerator;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;

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
