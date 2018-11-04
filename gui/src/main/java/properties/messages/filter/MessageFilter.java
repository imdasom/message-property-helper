package properties.messages.filter;

import com.konai.common.core.Expression;
import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;
import com.konai.common.vo.Value;
import com.konai.generate.core.KeyNameRule;
import com.konai.generate.core.MessagePropertyGenerator;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;

import java.util.List;
import java.util.function.Function;

public class MessageFilter {

    public static List<MessageProperty> getFailureMessage(List<SearchResult> searchResults, ResultClass searchLevel, KeyNameRule keyNameRule) {
        Function<SearchResult, MessageProperty> getFailCaseFunction = result -> {
            Expression failureExpressions = new Expression(result.getMessage().getOriginMessage());
            return MessagePropertyGenerator.generate(failureExpressions, keyNameRule);
        };
        return getFilteredMessages(searchResults, searchLevel, false, getFailCaseFunction);
    }

    public static List<MessageProperty> getSuccessMessages(List<SearchResult> searchResults, ResultClass searchLevel) {
        Function<SearchResult, MessageProperty> getSuccessCaseFunction = result -> {
            Key key = result.getKeyByLevel(searchLevel).get();
            Value value = new Value(result.getMessage().getOriginMessage());
            return new MessageProperty(key, value);
        };
        return getFilteredMessages(searchResults, searchLevel, true, getSuccessCaseFunction);
    }

    private static List<MessageProperty> getFilteredMessages(List<SearchResult> searchResults,
                                                      ResultClass searchLevel,
                                                      boolean isSuccess,
                                                      Function<SearchResult,
                                                              MessageProperty> getMessagePropertyFunction) {
        SearchResultFilter searchResultFilter = new SearchResultFilter();
        List<SearchResult> successSearchResluts = searchResultFilter.getSearchResult(searchResults, searchLevel, isSuccess);
        return searchResultFilter.getMessageProperties(successSearchResluts, getMessagePropertyFunction);
    }

}
