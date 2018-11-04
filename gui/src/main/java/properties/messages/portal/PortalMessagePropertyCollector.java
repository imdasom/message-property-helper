package properties.messages.portal;

import com.konai.collect.core.MessagePropertyCollector;
import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import properties.messages.wrapper.FileWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PortalMessagePropertyCollector {

    public List<Expression> collect(FileWrapper fileWrapper, PatternSearcher<Expression, Expression> patternSearcher) {
        MessagePropertyCollector<Expression, Expression> collector = MessagePropertyCollector.getInstance();
        List<Expression> inputExpressions = fileWrapper.getExpressions();
        List<Expression> collectExpressions = collector.collect(inputExpressions, patternSearcher);
        Set<Expression> uniqueExpressionList = new HashSet<>(collectExpressions);
        return uniqueExpressionList.stream().collect(Collectors.toList());
    }
}
