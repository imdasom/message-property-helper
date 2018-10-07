package com.konai.search.domain;

import com.konai.common.valueobject.Key;

import java.util.*;

public class SearchResult {
    private Message message;
    private SearchResultType resultType;
    private SearchResultMap resultMap;

    public SearchResult(Message message) {
        this.message = message;
        this.resultMap = new SearchResultMap();
    }

    public void putKeyResultMap(ResultClass resultClass, Key key) {
        List<Key> keyList = resultMap.get(resultClass);
        if(keyList == null) {
            keyList = new ArrayList<Key>();
        }
        keyList.add(key);
        resultMap.put(resultClass, keyList);
    }

    public Message getMessage() {
        return message;
    }

    public Map<ResultClass, List<Key>> getResultMap() {
        return resultMap;
    }

    public SearchResultType getResultType() {
        return resultType;
    }
    public void setResultType(SearchResultType resultType) {
        this.resultType = resultType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return Objects.equals(message, that.message) &&
                resultType == that.resultType &&
                Objects.equals(resultMap, that.resultMap);
    }

    @Override
    public int hashCode() {

        return Objects.hash(message, resultType, resultMap);
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
