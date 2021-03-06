package string.pattern.search.vo;

import string.pattern.common.util.CollectionUtils;
import string.pattern.common.vo.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SearchResult {
    private Message message;
    private SearchResultMap resultMap;

    public SearchResult(Message message) {
        if(message == null) {
            throw new NullPointerException("message is null");
        }
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

    public Optional<Key> getKeyByLevel(ResultClass filterLevel) {
        Key key = null;
        for (int level = 1; level <= filterLevel.getLevel(); level++) {
            List<Key> keyList = resultMap.get(ResultClass.getResultClassByLevel(level));
            if(!CollectionUtils.isEmpty(keyList)) {
                key = keyList.get(0);
                break;
            }
        }
        return Optional.ofNullable(key);
    }

    public Message getMessage() {
        return message;
    }

    public SearchResultMap getResultMap() {
        return resultMap;
    }

    public boolean isResultMapEmpty() {
        return resultMap == null || resultMap.size() < 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(resultMap, that.resultMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message.value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchResult{");
        sb.append("message=").append(message);
        sb.append(", resultMap=").append(resultMap.toString());
        sb.append('}');
        return sb.toString();
    }
}
