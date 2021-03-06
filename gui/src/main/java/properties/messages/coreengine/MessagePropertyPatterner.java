package properties.messages.coreengine;

import string.pattern.collect.core.KeyValueCollector;
import string.pattern.common.core.Expression;
import string.pattern.common.core.PatternSearcher;
import string.pattern.common.vo.Key;
import string.pattern.common.vo.KeyValue;
import string.pattern.generate.core.KeyNameRule;
import string.pattern.search.core.KeyValueSearcher;
import string.pattern.search.vo.Message;
import string.pattern.search.vo.SearchResult;
import properties.messages.filter.MessageFilter;
import properties.messages.wrapper.PatternRuleWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MessagePropertyPatterner {

    private KeyValueCollector collector = KeyValueCollector.getInstance();
    private KeyValueSearcher searcher = KeyValueSearcher.getInstance();

    public List<KeyValue> generate(PatternInformationGetter informationGetter,
                                   Map<Key, Message> resourceTokenList,
                                   KeyNameRule keyNameRule) {
        List<Expression> collectedValues = collect(informationGetter.getExpressions(), informationGetter.getCollectPattern());
        List<SearchResult> searchResultList = search(resourceTokenList, collectedValues);
        List<KeyValue> generatedMessages = MessageFilter.getFailureMessage(searchResultList, informationGetter.getSearchLevel(), keyNameRule);
        return generatedMessages;
    }

    public List<Expression> replace(List<KeyValue> messageProperties, PatternInformationGetter informationGetter) {
        ReplaceEngine replaceEngine = new ReplaceEngine();
        replaceEngine.set(messageProperties, informationGetter.getExpressions());
        List<PatternRuleWrapper> patternFairs = informationGetter.getPatternRuleWrappers();
        for(PatternRuleWrapper patternFair : patternFairs) {
            replaceEngine.replace(patternFair.searcher, patternFair.replacer);
        }
        return replaceEngine.get();
    }

    public List<Expression> collect(List<Expression> inputExpressions, PatternSearcher patternSearcher) {
        List<Expression> collectExpressions = collector.collect(inputExpressions, patternSearcher);
        Set<Expression> uniqueExpressionList = new HashSet<>(collectExpressions);
        return uniqueExpressionList.stream().collect(Collectors.toList());
    }

    public List<SearchResult> search(Map<Key, Message> resourceTokenList, List<Expression> uniqueExpressionList) {
        List<SearchResult> searchResults = searcher.search(uniqueExpressionList, resourceTokenList);
        return searchResults;
    }
}
