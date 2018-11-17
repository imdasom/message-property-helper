package properties.messages.portal;

import com.konai.collect.core.KeyValueCollector;
import com.konai.common.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.vo.Key;
import com.konai.common.vo.KeyValue;
import com.konai.generate.core.KeyNameRule;
import com.konai.search.core.KeyValueSearcher;
import com.konai.search.vo.Message;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import properties.messages.coreengine.ReplaceEngine;
import properties.messages.filter.MessageFilter;
import properties.messages.wrapper.FileWrapper;
import properties.messages.wrapper.ResourceBundleWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PortalMessagePropertyHelper {

    private KeyValueCollector collector = KeyValueCollector.getInstance();
    private KeyValueSearcher searcher = KeyValueSearcher.getInstance();

    public List<KeyValue> generate(FileWrapper fileWrapper,
                                   ResourceBundleWrapper resourceBundleWrapper,
                                   KeyNameRule keyNameRule,
                                   ResultClass searchLevel,
                                   PatternSearcher collectPattern) {
        List<Expression> collectedValues = collect(fileWrapper, collectPattern);
        List<SearchResult> searchResultList = search(resourceBundleWrapper, collectedValues);
        List<KeyValue> generatedMessages = MessageFilter.getFailureMessage(searchResultList, searchLevel, keyNameRule);
        return generatedMessages;
    }

    public List<Expression> replace(List<KeyValue> allMessageProperties, FileWrapper fileWrapper) {
        ReplaceEngine replaceEngine = new ReplaceEngine();
        ThymeleafTextValuePatternSearcher thymeleafTextPatternReplacer = new ThymeleafTextValuePatternSearcher();
        ThymeleafTextValuePatterner thymeleafTextValuePatterner = new ThymeleafTextValuePatterner();
//        BetweenHtmlTagPatternSearcher plainValuePatterner = new BetweenHtmlTagPatternSearcher();
        return replaceEngine.set(allMessageProperties, fileWrapper.getExpressions())
                .replace(thymeleafTextValuePatterner, thymeleafTextPatternReplacer)
//                .replace(plainValuePatterner, plainValuePatterner)
                .get();
    }

    public List<Expression> collect(FileWrapper fileWrapper, PatternSearcher patternSearcher) {
        List<Expression> inputExpressions = fileWrapper.getExpressions();
        List<Expression> collectExpressions = collector.collect(inputExpressions, patternSearcher);
        Set<Expression> uniqueExpressionList = new HashSet<>(collectExpressions);
        return uniqueExpressionList.stream().collect(Collectors.toList());
    }

    public List<SearchResult> search(ResourceBundleWrapper resourceBundleWrapper, List<Expression> uniqueExpressionList) {
        if(resourceBundleWrapper == null) {
            throw new IllegalArgumentException("resourceBundleWrapper is null");
        }
        Map<Key, Message> resourceTokenList = resourceBundleWrapper.getResourceMap();
        List<SearchResult> searchResults = searcher.search(uniqueExpressionList, resourceTokenList);
        return searchResults;
    }
}
