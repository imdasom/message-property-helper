package properties.messages.portal;

import com.konai.collect.core.MessagePropertyCollector;
import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.util.CollectionUtils;

import java.util.List;

public class PortalMessagePropertyCollector {

    private MessagePropertyCollector<Expression, Expression> collector = new MessagePropertyCollector<>();

    private PatternSearcher<Expression, Expression> thymeleafTextPatternSearcher = new ThymeleafTextPatternSearcher();
    private PatternSearcher<Expression, Expression> valuePatternSearcher = new ValuePatternSearcher();
    private PatternSearcher<Expression, Expression> betweenHtmlTagPatternSearcher = new BetweenHtmlTagPatternSearcher();

    public List<Expression> collect(List<Expression> inputExpressions) {
        //collect (thymeleaf pattern)
        List<Expression> thymeleafTextExpressions = collector.collect(inputExpressions, thymeleafTextPatternSearcher);
        List<Expression> valuePatternExpressions = collector.collect(thymeleafTextExpressions, valuePatternSearcher);
        //collect (plain pattern)
        List<Expression> plainPatternExpressions = collector.collect(inputExpressions, betweenHtmlTagPatternSearcher);
        //merge
        List<Expression> allValueExpressions = CollectionUtils.mergeList(valuePatternExpressions, plainPatternExpressions);
        return allValueExpressions;
    }
}
