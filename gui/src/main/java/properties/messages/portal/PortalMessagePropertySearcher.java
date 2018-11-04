package properties.messages.portal;

import com.konai.common.core.Expression;
import com.konai.common.vo.Key;
import com.konai.search.core.MessagePropertySearcher;
import com.konai.search.vo.Message;
import com.konai.search.vo.SearchResult;
import properties.messages.wrapper.ResourceBundleWrapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PortalMessagePropertySearcher {

    public List<SearchResult> search(ResourceBundleWrapper resourceBundleWrapper, List<Expression> uniqueExpressionList) {
        if(resourceBundleWrapper == null) {
            throw new IllegalArgumentException("resourceBundleWrapper is null");
        }
        Map<Key, Message> resourceTokenList = resourceBundleWrapper.getResourceMap();
        List<Message> valueList = uniqueExpressionList.stream()
                .map(value -> new Message(value.getValue()))
                .collect(Collectors.toList());
        MessagePropertySearcher searcher = MessagePropertySearcher.getInstance();
        List<SearchResult> searchResults = searcher.search(valueList, resourceTokenList);
        return searchResults;
    }
}
