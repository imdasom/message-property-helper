package com.konai.search;

import com.konai.search.core.MessageSearchManager;
import org.junit.Test;

import java.io.IOException;

public class MessageSearchManagerTest {

    @Test
    public void findFromResourceFile() throws IOException {
        String[] inputDatas = new String[]{
                "제휴 계약 관리",
                "선매입",
                "선매입 계약"
        };
		String location = ".\\resources\\";
        String bundleName = "messages";
        MessageSearchManager messageSearchManager = new MessageSearchManager();
        messageSearchManager.findFromResourceFile(inputDatas, location, bundleName);
    }
}
