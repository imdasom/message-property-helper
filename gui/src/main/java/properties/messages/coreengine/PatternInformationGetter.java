package properties.messages.coreengine;

import string.pattern.common.core.Expression;
import string.pattern.common.core.PatternSearcher;
import string.pattern.search.vo.ResultClass;
import properties.messages.wrapper.PatternRuleWrapper;

import java.util.List;

public interface PatternInformationGetter {
    String getKeyName();
    ResultClass getSearchLevel();
    List<Expression> getExpressions();
    PatternSearcher getCollectPattern();
    List<PatternRuleWrapper> getPatternRuleWrappers();
}
