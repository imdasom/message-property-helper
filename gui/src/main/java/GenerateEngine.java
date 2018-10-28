
import com.konai.collect.core.MessagePropertyCollector;
import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;
import com.konai.common.vo.Value;
import com.konai.generate.core.KeyNameRule;
import com.konai.generate.core.MessagePropertyGenerator;
import com.konai.search.core.MessagePropertySearcher;
import com.konai.search.vo.Message;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import filter.SearchResultFilter;
import portal.ThymeleafTextPatternSearcher;
import portal.ValuePatternSearcher;
import wrapper.FileWrapper;
import wrapper.ResourceBundleWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenerateEngine {

    private MessagePropertyCollector<Expression, Expression> collector = new MessagePropertyCollector<>();
    private MessagePropertySearcher searcher = new MessagePropertySearcher();

    private ThymeleafTextPatternSearcher thymeleafTextPatternSearcher = new ThymeleafTextPatternSearcher();
    private PatternSearcher<Expression, Expression> valuePatternSearcher = new ValuePatternSearcher();

    private ResourceBundleWrapper resourceBundleWrapper;
    private FileWrapper fileWrapper;

    public GenerateEngine(ResourceBundleWrapper resourceBundleWrapper, FileWrapper fileWrapper) {
        this.resourceBundleWrapper = resourceBundleWrapper;
        this.fileWrapper = fileWrapper;
    }

    public List<SearchResult> getSearchResults() {
        List<Expression> readLineExpressions = fileWrapper.getExpressions();
        Map<Key, Message> resourceTokenList = resourceBundleWrapper.getResourceMap();

        //collect
        List<Expression> thymeleafTextExpressions = collector.collect(readLineExpressions, thymeleafTextPatternSearcher);
        List<Expression> valuePatternExpressions = collector.collect(thymeleafTextExpressions, valuePatternSearcher);

        //remove duplicate
        Set<Expression> uniqueExpressionList = new HashSet<>(valuePatternExpressions);

        //search
        List<Message> valueList = uniqueExpressionList.stream()
                .map(value -> new Message(value.getValue()))
                .collect(Collectors.toList());
        List<SearchResult> searchResults = searcher.search(valueList, resourceTokenList);

        return searchResults;
    }

    public List<MessageProperty> getFailureMessage(List<SearchResult> searchResults, ResultClass searchLevel, KeyNameRule keyNameRule) {
        Function<SearchResult, MessageProperty> getFailCaseFunction = result -> {
            Expression failureExpressions = new Expression(result.getMessage().getOriginMessage());
            return MessagePropertyGenerator.generate(failureExpressions, keyNameRule);
        };
        return getFilteredMessages(searchResults, searchLevel, false, getFailCaseFunction);
    }

    public List<MessageProperty> getSuccessMessages(List<SearchResult> searchResults, ResultClass searchLevel) {
        Function<SearchResult, MessageProperty> getSuccessCaseFunction = result -> {
            Key key = result.getKeyByLevel(searchLevel).get();
            Value value = new Value(result.getMessage().getOriginMessage());
            return new MessageProperty(key, value);
        };
        return getFilteredMessages(searchResults, searchLevel, true, getSuccessCaseFunction);
    }

    private List<MessageProperty> getFilteredMessages(List<SearchResult> searchResults,
                                                      ResultClass searchLevel,
                                                      boolean isSuccess,
                                                      Function<SearchResult,
                                                              MessageProperty> getMessagePropertyFunction) {
        SearchResultFilter searchResultFilter = new SearchResultFilter();
        List<SearchResult> successSearchResluts = searchResultFilter.getSearchResult(searchResults, searchLevel, isSuccess);
        return searchResultFilter.getMessageProperties(successSearchResluts, getMessagePropertyFunction);
    }
}
