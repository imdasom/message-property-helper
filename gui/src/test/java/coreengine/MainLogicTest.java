package coreengine;

import com.konai.collect.core.KeyValueCollector;
import com.konai.common.core.Expression;
import com.konai.common.util.FileUtils;
import com.konai.common.vo.Key;
import com.konai.common.vo.KeyValue;
import com.konai.common.vo.Value;
import com.konai.generate.core.KeyNameRule;
import com.konai.generate.core.KeyValueGenerator;
import com.konai.replace.core.KeyValueReplacer;
import com.konai.search.core.KeyValueSearcher;
import com.konai.search.util.KeyValueTokenizer;
import com.konai.search.vo.Message;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import org.junit.Test;
import properties.messages.filter.SearchResultFilter;
import properties.messages.portal.PortalKeyNameRule;
import properties.messages.portal.ThymeleafTextValuePatternSearcher;
import properties.messages.portal.ThymeleafTextValuePatterner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainLoginTest {

    @Test
    public void main() throws IOException {

        //module instatiate
        KeyValueTokenizer tokenizer = new KeyValueTokenizer();
        KeyValueSearcher searcher = KeyValueSearcher.getInstance();
        KeyValueReplacer replacer = new KeyValueReplacer();
        KeyValueCollector collector = KeyValueCollector.getInstance();

        //resource : message properties bundle
        String location = ".\\src\\test\\resources\\";
        String bundleName = "messages";
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, new Locale("ko", "KR"));
        Map<String, String> messageProertyMap = tokenizer.getMapFromResource(bundle);
        Map<Key, Message> resourceTokenList = tokenizer.getTokenListFromMap(messageProertyMap);

        //resource : html file
        InputStream inputStream = FileUtils.getInputStream(new File(".\\src\\test\\resources\\html\\productView.html"));
        List<String> lines = FileUtils.readLines(inputStream);
        List<Expression> readLineExpressions = lines.stream().map(Expression::new).collect(Collectors.toList());

        //regular expression pattern
        ThymeleafTextValuePatternSearcher thymeleafTextValuePatternSearcher = new ThymeleafTextValuePatternSearcher();
        ThymeleafTextValuePatterner thymeleafTextValuePatterner = new ThymeleafTextValuePatterner();

        //key name rule
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", resourceTokenList);

        //collect
        List<Expression> thymeleafTextExpressions = collector.collect(readLineExpressions, thymeleafTextValuePatternSearcher);

        //search
        List<Message> valueList = thymeleafTextExpressions.stream()
                .map(value -> new Message(value.getValue()))
                .collect(Collectors.toList());
        List<SearchResult> searchResults = searcher.search(valueList, resourceTokenList);

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
                thymeleafTextValuePatterner,
                thymeleafTextValuePatternSearcher);

        //replace
        List<Expression> afterLines2 = replacer.replace(oldMessageProperties,
                afterLines,
                thymeleafTextValuePatterner,
                thymeleafTextValuePatternSearcher);

        for (KeyValue keyValue : oldMessageProperties) {
            System.out.println(keyValue.toString());
        }

        for(Expression e : afterLines2) {
            System.out.println(e.getValue());
        }
    }
}

