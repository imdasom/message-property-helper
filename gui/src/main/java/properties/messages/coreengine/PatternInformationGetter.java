package properties.messages.coreengine;

import com.konai.common.core.Expression;
import com.konai.common.core.PatternSearcher;
import com.konai.search.vo.ResultClass;
import properties.messages.wrapper.PatternRuleWrapper;

import java.util.List;

public interface PatternInformationGetter {
    String getKeyName();
    ResultClass getSearchLevel();
    List<Expression> getExpressions();
    PatternSearcher getCollectPattern();
    List<PatternRuleWrapper> getPatternRuleWrappers();
}
