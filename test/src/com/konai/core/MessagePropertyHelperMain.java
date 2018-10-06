package com.konai.core;

import org.junit.Test;

public class MessagePropertyHelperMain {

    @Test
    public void main() {
        // MessageCollector collector;
        // MessageSearcher searcher;
        // MessageGenerator generator;
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
