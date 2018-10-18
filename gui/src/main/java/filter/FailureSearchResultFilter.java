package filter;

import com.konai.common.core.Expression;
import com.konai.common.vo.MessageProperty;
import com.konai.generate.core.KeyNameRule;
import com.konai.generate.core.MessagePropertyGenerator;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FailureSearchResultFilter extends SearchResultFilter {

    public List<MessageProperty> generateFailureMessageProperties(KeyNameRule keyNameRule, Set<SearchResult> failureSearchReuslts) {
        MessagePropertyGenerator generator = new MessagePropertyGenerator();
        List<Expression> failureExpressions = failureSearchReuslts.stream()
                .map(result -> new Expression(result.getMessage().getOriginMessage()))
                .collect(Collectors.toList());
        return generator.generate(failureExpressions, keyNameRule);
    }

    public List<SearchResult> getFailureSearchResult(List<SearchResult> searchResults, ResultClass filterLevel) {
        List<SearchResult> failureSearchResults = new ArrayList<>();
        for (SearchResult searchResult : searchResults) {
            if(isFailCase(searchResult, filterLevel)) {
                failureSearchResults.add(searchResult);
            }
        }
        return failureSearchResults;
    }

}
