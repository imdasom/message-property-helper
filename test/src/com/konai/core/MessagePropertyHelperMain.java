package com.konai.core;

import com.konai.collect.core.MessagePropertyCollector;
import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.core.MessageTokenizer;
import com.konai.common.util.FileUtils;
import com.konai.common.valueobject.MessageProperty;
import com.konai.generator.PortalKeyNameRule;
import com.konai.generator.core.KeyNameRule;
import com.konai.generator.core.MessagePropertyGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessagePropertyHelperMain {

    @Test
    public void main() throws IOException {

        InputStream inputStream = FileUtils.getInputStream(new File(".\\test\\resources\\html\\productView.html"));
        List<String> lines = FileUtils.readLines(inputStream);
        List<Expression> expressions = lines.stream().map(Expression::new).collect(Collectors.toList());

        MessageTokenizer tokenizer = new MessageTokenizer();
        MessagePropertyGenerator generator = new MessagePropertyGenerator();
        MessagePropertyCollector<Expression, Expression> collector = new MessagePropertyCollector<>();
        PatternSearcher<Expression, Expression> valuePatternSearcher = new ValuePatternSearcher();
        PatternSearcher<Expression, Expression> thymeleafTextPatternSearcher = new ThymeleafTextPatternSearcher();

        List<Expression> thymeleafTextExpressions = collector.collect(expressions, thymeleafTextPatternSearcher);
        List<Expression> valuePatternExpressions = collector.collect(thymeleafTextExpressions, valuePatternSearcher);

        String location = ".\\test\\resources\\";
        String bundleName = "messages";
        Map<String, String> messageProertyMap = tokenizer.getMapFromResource(location, bundleName);
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", messageProertyMap);

        List<MessageProperty> messageProperties = generator.generate(valuePatternExpressions, keyNameRule);

        // MessageSearcher searcher;
        // MessageModelConverter converter;
        // MessageReplacer replacer;

        // List<Message> collectMessages = collector.collect(resourcePath);
        // List<SearchResult> searchResults = searcher.search(collectMessages, messagePropertiesFilePath);
        // List<SearchResult> failuerResults = searchResults.stream().filter(SearchResultType.Failuer인경우);

        // Map<Message, PropertyKey> generatedResult = generator.generate(failuerResults, PREFIX);
        // FileWriter fileWriter = new FileWriter(messagePropertiesFilePath);
        // fileWriter.writeByLine(generatedResult, function(key, value) { return "key=value"; });

        // List<MessageProperty> replaceItem = converter(searchResults, generatedResult를 통해 key,value로 이뤄진 결과를 만든다);
        // (MessageProperty는 key,value로 이루어져 있다. 파일에서 해당 value를 읽어 key로 교체할 것이다)
        // boolean replace = replacer(resourcePath, replaceItem);
    }
}
