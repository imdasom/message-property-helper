package coreengine;

import string.pattern.collect.core.KeyValueCollector;
import string.pattern.common.core.Expression;
import string.pattern.common.util.FileUtils;
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
import custom.portal.PortalKeyNameRule;
import custom.portal.pattern.ThymeleafTextValuePatternSearcher;
import custom.portal.pattern.ThymeleafTextValuePatterner;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainLogicTest {

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
        File htmlFile = new File(".\\src\\test\\resources\\html\\productView.html");
        InputStream inputStream = FileUtils.getInputStream(htmlFile);
        List<String> lines = FileUtils.readLines(inputStream);
        List<Expression> readLineExpressions = lines.stream().map(Expression::new).collect(Collectors.toList());

        //regular expression pattern
        ThymeleafTextValuePatternSearcher thymeleafTextValuePatternSearcher = ThymeleafTextValuePatternSearcher.getInstance();
        ThymeleafTextValuePatterner thymeleafTextValuePatterner = new ThymeleafTextValuePatterner();

        //key name rule
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", resourceTokenList);

        //collect
        List<Expression> thymeleafTextExpressions = collector.collect(readLineExpressions, thymeleafTextValuePatternSearcher);

        //search
        List<SearchResult> searchResults = searcher.search(thymeleafTextExpressions, resourceTokenList);

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

        for (KeyValue keyValue : newMessageProperties) {
            System.out.println(keyValue.getKey().getValue() + "=" + keyValue.getValue().getValue());
        }

        for(Expression e : afterLines2) {
            System.out.println(e.getValue());
        }
        
        //write file
        OutputStream outputStream = FileUtils.getOutputStream(htmlFile);
        for (Expression e : afterLines2) {
            outputStream.write(e.getValue().getBytes());
            outputStream.write("\n".getBytes());
        }
        outputStream.flush();
        outputStream.close();
        
        //set properties
        Properties properties = new Properties();
        for (KeyValue keyValue : newMessageProperties) {
            properties.setProperty(keyValue.getKey().getValue(), keyValue.getValue().getValue());
        }
        
        //ready properties output stream
        OutputStream propertiesOutputStream_default  = new FileOutputStream(location + "\\messages.properties", true);
        OutputStream propertiesOutputStream_ko = new FileOutputStream(location + "\\messages_ko.properties", true);
        OutputStream propertiesOutputStream_en = new FileOutputStream(location + "\\messages_en.properties", true);
        OutputStream[] propertiesOutputStreamList = new OutputStream[] {
            propertiesOutputStream_default, propertiesOutputStream_ko, propertiesOutputStream_en
        };
        
        //store properties
        Arrays.stream(propertiesOutputStreamList).forEach(outputStrema1 -> {
            try {
                outputStream1.write("\n\n".getBytes());
                properties.store(outputStream1, "comment");
            } catch (IOException e) {
                e.printStackTrace();
        });
    }
}

