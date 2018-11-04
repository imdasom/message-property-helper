package properties.messages.coreengine;

import com.konai.common.vo.MessageProperty;
import com.konai.generate.core.KeyNameRule;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import properties.messages.filter.MessageFilter;

import java.util.List;

public class PortalMessagePropertyGenerator {

    public List<MessageProperty> generate(List<SearchResult> searchResultList, KeyNameRule keyNameRule, ResultClass searchLevel) {
        return MessageFilter.getFailureMessage(searchResultList, searchLevel, keyNameRule);
    }
}
