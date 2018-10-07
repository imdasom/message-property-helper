package com.konai.search.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResult {
    private Message message;
    private SearchResultType resultType;
    private Map<ResultClass, List<String>> resultMap;

    public SearchResult(Message message) {
        this.message = message;
        this.resultMap = new HashMap<ResultClass, List<String>>();
    }

    public void putKeyResultMap(ResultClass resultClass, String key) {
        List<String> keyList = resultMap.get(resultClass);
        if(keyList == null) {
            keyList = new ArrayList<String>();
        }
        keyList.add(key);
        resultMap.put(resultClass, keyList);
    }

    public Map<ResultClass, List<String>> getResultMap() {
        return resultMap;
    }

    public void setResultType(SearchResultType resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchResult{");
        sb.append("message=").append(message);
        sb.append(", resultType=").append(resultType);
        sb.append(", resultMap=").append(resultMap.toString());
        sb.append('}');
        return sb.toString();
    }
}
