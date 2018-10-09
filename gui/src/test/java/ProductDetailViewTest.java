import com.konai.collect.core.MessagePropertyCollector;
import com.konai.common.core.Expression;
import com.konai.common.util.FileUtils;
import com.konai.common.util.StringUtils;
import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;
import com.konai.generate.core.KeyNameRule;
import com.konai.generate.core.MessagePropertyGenerator;
import com.konai.replace.core.MessagePropertyReplacer;
import com.konai.search.core.MessagePropertySearcher;
import com.konai.search.util.MessageTokenizer;
import com.konai.search.vo.Message;
import com.konai.search.vo.ResultClass;
import com.konai.search.vo.SearchResult;
import filter.FailureSearchResultFilter;
import filter.SuccessSearchResultFilter;
import org.junit.Test;
import rule.PortalKeyNameRule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductDetailViewTest {

    @Test
    public void main() throws IOException {

        //module instatiate
        MessageTokenizer tokenizer = new MessageTokenizer();
        MessagePropertySearcher searcher = new MessagePropertySearcher();
        MessagePropertyReplacer replacer = new MessagePropertyReplacer();
        MessagePropertyGenerator generator = new MessagePropertyGenerator();
        MessagePropertyCollector<Expression, Expression> collector = new MessagePropertyCollector<>();

        //resource : message properties bundle
        String location = ".\\src\\test\\resources\\";
        String bundleName = "messages";
        ResourceBundle bundle = FileUtils.getResourceBundle(location, bundleName, new Locale("ko", "KR"));
        Map<String, String> messageProertyMap = tokenizer.getMapFromResource(bundle);

        //resource : html file
        InputStream inputStream = FileUtils.getInputStream(new File(".\\src\\test\\resources\\html\\productDetailView.html"));
        List<String> lines = FileUtils.readLines(inputStream);
        List<Expression> readLineExpressions = lines.stream()
                .filter(value -> !StringUtils.isEmpty(value))
                .map(Expression::new)
                .collect(Collectors.toList());

        //regular expression pattern
        BetweenHtmlTagPatternSearcher betweenHtmlTagPatternSearcher = new BetweenHtmlTagPatternSearcher();

        //key name rule
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", messageProertyMap);

        //collect
        List<Expression> collectedExpression = collector.collect(readLineExpressions, betweenHtmlTagPatternSearcher);

        //search
        Map<Key, Message> resourceTokenList = tokenizer.getTokenListFromMap(messageProertyMap);
        List<Message> valueList = collectedExpression.stream()
                .map(value -> new Message(value.getValue()))
                .collect(Collectors.toList());
        List<SearchResult> searchResults = searcher.search(valueList, resourceTokenList);

        // generate or get
        FailureSearchResultFilter failureFilter = new FailureSearchResultFilter();
        List<SearchResult> failureSearchReuslts = failureFilter.getFailureSearchResult(searchResults, ResultClass.TotalSimilar);
        List<MessageProperty> newMessageProperties = failureFilter.getFailureMessageProperties(keyNameRule, failureSearchReuslts);
        SuccessSearchResultFilter successFilter = new SuccessSearchResultFilter();
        List<SearchResult> successSearchResluts = successFilter.getSuccessSearchResult(searchResults, ResultClass.TotalSimilar);
        List<MessageProperty> oldMessageProperties = successFilter.getMessageProperties(successSearchResluts, ResultClass.TotalSimilar);
        oldMessageProperties.addAll(newMessageProperties);

        //replace
        List<Expression> afterLines = replacer.replace(oldMessageProperties,
                readLineExpressions,
                betweenHtmlTagPatternSearcher,
                betweenHtmlTagPatternSearcher);

        for(Expression e : afterLines) {
            System.out.println(e.getValue());
        }
    }
}

