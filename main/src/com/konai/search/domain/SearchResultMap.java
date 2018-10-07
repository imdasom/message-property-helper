package com.konai.search.domain;

import com.konai.common.valueobject.Key;

import java.util.HashMap;
import java.util.List;

public class SearchResultMap extends HashMap<ResultClass, List<Key>> {

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        SearchResultMap that = (SearchResultMap) o;
        if(that.size() != that.size()) return false;
        for(Entry<ResultClass, List<Key>> entry : this.entrySet()) {
            ResultClass resultClass = entry.getKey();
            List<Key> keyList1 = entry.getValue();
            List<Key> keyList2 = that.get(resultClass);
            if (keyList2 == null) return false;
            for(Key key1 : keyList1) {
                boolean isEquals = false;
                for (Key key2 : keyList2) {
                    if(key1.equals(key2)) {
                        isEquals = true;
                        break;
                    }
                }
                if(!isEquals) {
                    return false;
                }
            }
        }
        return true;
    }
}
