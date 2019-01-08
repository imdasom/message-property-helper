package coreengine;

import string.pattern.collect.core.KeyValueCollector;
import string.pattern.common.core.Expression;
import string.pattern.common.util.FileUtils;
import string.pattern.common.util.StringUtils;
import string.pattern.common.vo.Key;
import string.pattern.common.vo.KeyValue;
import string.pattern.common.vo.Value;
import string.pattern.generate.core.KeyNameRule;
import string.pattern.generate.core.KeyValueGenerator;
import string.pattern.replace.core.KeyValueReplacer;
import string.pattern.search.core.KeyValueSearcher;
import string.pattern.search.util.KeyValueTokenizer;
import string.pattern.search.vo.Message;
import string.pattern.search.vo.ResultClass;
import string.pattern.search.vo.SearchResult;
import org.junit.Test;
import properties.messages.filter.SearchResultFilter;
import custom.portal.pattern.BetweenHtmlTagPatternSearcher;
import custom.portal.PortalKeyNameRule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductDetailViewTest {

    @Test
    public void main() throws IOException {

        //module instatiate
        KeyValueTokenizer tokenizer = new KeyValueTokenizer();
        KeyValueSearcher searcher = KeyValueSearcher.getInstance();
        KeyValueReplacer replacer = new KeyValueReplacer();
        KeyValueGenerator generator = new KeyValueGenerator();
        KeyValueCollector collector = KeyValueCollector.getInstance();

        //resource : message properties bundle
        String location = ".\\src\\test\\resources\\";
        String bundleName = "messages";
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, new Locale("ko", "KR"));
        Map<String, String> messageProertyMap = tokenizer.getMapFromResource(bundle);
        Map<Key, Message> resourceTokenList = tokenizer.getTokenListFromMap(messageProertyMap);

        //resource : html file
        InputStream inputStream = FileUtils.getInputStream(new File(".\\src\\test\\resources\\html\\productDetailView.html"));
        List<String> lines = FileUtils.readLines(inputStream);
        List<Expression> readLineExpressions = lines.stream()
                .filter(value -> !StringUtils.isEmpty(value))
                .map(Expression::new)
                .collect(Collectors.toList());

        //regular expression pattern
        BetweenHtmlTagPatternSearcher betweenHtmlTagPatternSearcher = new BetweenHtmlTagPatternSearcher();

        //key name rule
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", resourceTokenList);

        //collect
        List<Expression> collectedExpression = collector.collect(readLineExpressions, betweenHtmlTagPatternSearcher);

        //search
        List<SearchResult> searchResults = searcher.search(collectedExpression, resourceTokenList);

        // generate or get
        SearchResultFilter searchResultFilter = new SearchResultFilter();
        List<SearchResult> failureSearchReuslts = searchResultFilter.getSearchResult(searchResults, ResultClass.TotalSimilar, false);
        List<KeyValue> newMessageProperties = searchResultFilter.getMessageProperties(failureSearchReuslts, new Function<SearchResult, KeyValue>() {
            @Override
            public KeyValue apply(SearchResult result) {
                Expression failureExpressions = new Expression(result.getMessage().getOriginMessage());
                return KeyValueGenerator.generate(failureExpressions, keyNameRule);
            }
        });

        List<SearchResult> successSearchResluts = searchResultFilter.getSearchResult(searchResults, ResultClass.TotalSimilar, true);
        List<KeyValue> oldMessageProperties = searchResultFilter.getMessageProperties(successSearchResluts, new Function<SearchResult, KeyValue>() {
            @Override
            public KeyValue apply(SearchResult result) {
                Key key = result.getKeyByLevel(ResultClass.TotalSimilar).get();
                Value value = new Value(result.getMessage().getOriginMessage());
                return new KeyValue(key, value);
            }
        });
        oldMessageProperties.addAll(newMessageProperties);

        //replace
        List<Expression> afterLines = replacer.replace(oldMessageProperties,
                readLineExpressions,
                betweenHtmlTagPatternSearcher,
                betweenHtmlTagPatternSearcher);

        for(Expression e : afterLines) {
            System.out.println(e.getValue());
        }
    }
}

