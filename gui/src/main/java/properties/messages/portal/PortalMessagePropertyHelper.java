package properties.messages.portal;

import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.vo.MessageProperty;
import com.konai.generate.core.KeyNameRule;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import properties.messages.coreengine.PortalMessagePropertyGenerator;
import properties.messages.wrapper.FileWrapper;
import properties.messages.wrapper.ResourceBundleWrapper;

import java.util.List;

public class PortalMessagePropertyHelper {

    private PortalMessagePropertyCollector collector = new PortalMessagePropertyCollector();
    private PortalMessagePropertySearcher searcher = new PortalMessagePropertySearcher();
    private PortalMessagePropertyGenerator generator = new PortalMessagePropertyGenerator();

    public List<MessageProperty> generate(FileWrapper fileWrapper,
                                          ResourceBundleWrapper resourceBundleWrapper,
                                          KeyNameRule keyNameRule,
                                          ResultClass searchLevel,
                                          PatternSearcher<Expression, Expression> collectPattern) {
        List<Expression> collectedValues = collector.collect(fileWrapper, collectPattern);
        List<SearchResult> searchResultList = searcher.search(resourceBundleWrapper, collectedValues);
        List<MessageProperty> generatedMessages = generator.generate(searchResultList, keyNameRule, searchLevel);
        return generatedMessages;
    }
}
