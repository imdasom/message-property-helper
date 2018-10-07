package com.konai.search;

import com.konai.search.core.MessageSearchManager;
import com.konai.search.domain.SearchResult;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class MessagePropertySearchManagerTest {

    @Test
    public void findFromResourceFile() throws IOException {
        String[] inputDatas = new String[]{
                "제휴 계약 관리",
                "선매입",
                "선매입 계약",
                "안녕하세요"
        };
		String location = ".\\test\\resources\\";
        String bundleName = "messages";
        MessageSearchManager messageSearchManager = new MessageSearchManager();
        List<SearchResult> searchResults = messageSearchManager.findFromResourceFile(inputDatas, location, bundleName);
        for(SearchResult s : searchResults) {
            System.out.println(s.toString());
        }
    }
}
