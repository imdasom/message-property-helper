package filter;

import com.konai.common.vo.Key;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;

import java.util.Optional;

public class SearchResultFilter {

    protected boolean isFailCase(SearchResult searchResult, ResultClass filterLevel) {
        boolean isFailCase = false;
        if(searchResult.isResultMapEmpty()) {
            isFailCase = true;
        } else {
            Optional<Key> key = searchResult.getKeyByLevel(filterLevel);
            if(!key.isPresent()) {
                isFailCase = true;
            }
        }
        return isFailCase;
    }
}
