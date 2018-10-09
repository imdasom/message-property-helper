package com.konai.search;

import com.konai.common.util.FileUtils;
import com.konai.common.vo.Key;
import com.konai.search.core.MessageSearchEngine;
import com.konai.search.core.MessagePropertySearcher;
import com.konai.search.util.MessageTokenizer;
import com.konai.search.vo.Message;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import com.konai.search.vo.SearchResultType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class MessagePropertySearchManagerTest {

    @Test
    public void findFromResourceFile() throws IOException {
        List<Message> inputDatas = new ArrayList<>();
        inputDatas.add(new Message("제휴 계약 관리"));
        inputDatas.add(new Message("선매입"));
        inputDatas.add(new Message("선매입 계약"));
        inputDatas.add(new Message("안녕하세요"));

		String location = ".\\test\\resources\\";
        String bundleName = "messages";
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, new Locale("ko", "KR"));
        MessageTokenizer tokenizer = new MessageTokenizer();
        Map<String, String> resourcesMap = tokenizer.getMapFromResource(bundle);
        Map<Key, Message> resourceTokens = tokenizer.getTokenListFromMap(resourcesMap);
        MessagePropertySearcher messagePropertySearcher = new MessagePropertySearcher();
        List<SearchResult> searchResults = messagePropertySearcher.search(inputDatas, resourceTokens);
        for(SearchResult s : searchResults) {
            System.out.println(s.toString());
        }
    }

    @Test
    public void searchSingle() throws IOException {
        MessageTokenizer tokenizer = new MessageTokenizer();
        MessageSearchEngine searchEngine = new MessageSearchEngine();

        String location = ".\\test\\resources\\";
        String bundleName = "messages";
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, new Locale("ko", "KR"));
        Map<String, String> messageProertyMap = tokenizer.getMapFromResource(bundle);
        Map<Key, Message> resourceTokenList = tokenizer.getTokenListFromMap(messageProertyMap);

        SearchResult result = searchEngine.searchMessage(new Message("상품명"), resourceTokenList);
        Assert.assertEquals(1, result.getResultMap().get(ResultClass.TotalEqual));
    }

    @Test
    public void searchList() throws IOException {
        MessageTokenizer tokenizer = new MessageTokenizer();
        MessageSearchEngine searchEngine = new MessageSearchEngine();

        String location = ".\\test\\resources\\";
        String bundleName = "messages";
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, new Locale("ko", "KR"));
        Map<String, String> messageProertyMap = tokenizer.getMapFromResource(bundle);
        Map<Key, Message> resourceTokenList = tokenizer.getTokenListFromMap(messageProertyMap);

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("제휴 계약 관리"));
        List<SearchResult> results = searchEngine.searchMessageList(messages, resourceTokenList);
        List<SearchResult> expectedResults = new ArrayList<>();
        SearchResult searchResult = new SearchResult(new Message("제휴 계약 관리"));
        searchResult.putKeyResultMap(ResultClass.TotalEqual, new Key("PROD_MANA_0001"));
        searchResult.putKeyResultMap(ResultClass.TotalSimilar, new Key("PROD_MANA_0011"));
        searchResult.putKeyResultMap(ResultClass.TotalSimilar, new Key("PROD_MANA_0012"));
        searchResult.putKeyResultMap(ResultClass.PartialEquals, new Key("PROD_MANA_0005"));
        expectedResults.add(searchResult);

        Assert.assertEquals(expectedResults, results);
    }
}
