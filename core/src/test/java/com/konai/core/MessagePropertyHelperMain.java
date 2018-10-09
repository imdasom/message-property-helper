package com.konai.core;

import com.konai.collect.core.MessagePropertyCollector;
import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.util.CollectionUtils;
import com.konai.common.util.FileUtils;
import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;
import com.konai.common.vo.Value;
import com.konai.generate.core.KeyNameRule;
import com.konai.generate.core.MessagePropertyGenerator;
import com.konai.generator.PortalKeyNameRule;
import com.konai.replace.core.MessagePropertyReplacer;
import com.konai.search.core.MessagePropertySearcher;
import com.konai.search.util.MessageTokenizer;
import com.konai.search.vo.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class MessagePropertyHelperMain {

    @Test
    public void main() throws IOException {

        //module instatiate
        MessageTokenizer tokenizer = new MessageTokenizer();
        MessagePropertySearcher searcher = new MessagePropertySearcher();
        MessagePropertyReplacer replacer = new MessagePropertyReplacer();
        MessagePropertyGenerator generator = new MessagePropertyGenerator();
        MessagePropertyCollector<Expression, Expression> collector = new MessagePropertyCollector<>();

        //resource : message properties bundle
        String location = ".\\src\\test\\resources\\";
        String bundleName = "messages";
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, new Locale("ko", "KR"));
        Map<String, String> messageProertyMap = tokenizer.getMapFromResource(bundle);

        //resource : html file
        InputStream inputStream = FileUtils.getInputStream(new File(".\\src\\test\\resources\\html\\productView.html"));
        List<String> lines = FileUtils.readLines(inputStream);
        List<Expression> readLineExpressions = lines.stream().map(Expression::new).collect(Collectors.toList());

        //regular expression pattern
        ThymeleafTextPatternSearcher thymeleafTextPatternSearcher = new ThymeleafTextPatternSearcher();
        PatternSearcher<Expression, Expression> valuePatternSearcher = new ValuePatternSearcher();
        ThymeleafTextValuePatterner thymeleafTextValuePatterner = new ThymeleafTextValuePatterner();

        //key name rule
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", messageProertyMap);

        //collect
        List<Expression> thymeleafTextExpressions = collector.collect(readLineExpressions, thymeleafTextPatternSearcher);
        List<Expression> valuePatternExpressions = collector.collect(thymeleafTextExpressions, valuePatternSearcher);

        //search
        Map<Key, Message> resourceTokenList = tokenizer.getTokenListFromMap(messageProertyMap);
        List<Message> valueList = valuePatternExpressions.stream()
                .map(value -> new Message(value.getValue()))
                .collect(Collectors.toList());
        List<SearchResult> searchResults = searcher.search(valueList, resourceTokenList);

        //compress result
        //List<SearchResult> compressResult = compresor.compress(ResultClass.Total, ResultSubClass.Similar, Order.First);

        List<SearchResult> failureResults = searchResults.stream()
                .filter(searchResult -> searchResult.getResultMap() == null || searchResult.getResultMap().size() < 1)
                .collect(Collectors.toList());
        List<Expression> failureExpressions = failureResults.stream()
                .map(searchResult -> new Expression(searchResult.getMessage().getOriginMessage()))
                .collect(Collectors.toList());
        List<MessageProperty> newMessageProperties = generator.generate(failureExpressions, keyNameRule);

        //old message properties
        List<MessageProperty> oldMessageProperty = new ArrayList<>();
        for (Map.Entry<String, String> entry : messageProertyMap.entrySet()) {
            oldMessageProperty.add(new MessageProperty(new Key(entry.getKey()), new Value(entry.getValue())));
        }

        //replace
        List<Expression> afterLines = replacer.replace(newMessageProperties,
                readLineExpressions,
                thymeleafTextValuePatterner,
                thymeleafTextPatternSearcher);

        //replace
        List<Expression> afterLines2 = replacer.replace(oldMessageProperty,
                afterLines,
                thymeleafTextValuePatterner,
                thymeleafTextPatternSearcher);

        for(Expression e : afterLines2) {
            System.out.println(e.getValue());
        }
    }
}
