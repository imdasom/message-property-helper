package string.pattern.replace.core;

import string.pattern.common.core.PatternReplacer;
import string.pattern.common.core.PatternSearcher;
import string.pattern.common.core.Expression;
import string.pattern.common.vo.Key;
import string.pattern.common.vo.KeyValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KeyValueReplacer {

    public List<Expression> replace(List<KeyValue> resourceExpression,
                                    List<Expression> targetExpressions,
                                    PatternSearcher patternSearcher,
                                    PatternReplacer patternReplacer) {
        List<Expression> afterReplaces = new ArrayList<>();
        for (Expression targetExpression : targetExpressions) {
            List<Expression> matchValues = patternSearcher.get(targetExpression);
            Expression afterExpression = replaceValues(resourceExpression, targetExpression, matchValues, patternReplacer);
            afterReplaces.add(afterExpression);
        }
        return afterReplaces;
    }

    private Expression replaceValues(List<KeyValue> resourceExpression, Expression targetExpression, List<Expression> matchValues, PatternReplacer patternReplacer) {
        Expression expression = targetExpression;
        for(Expression matchValue : matchValues) {
            expression = replaceValue(expression, matchValue, resourceExpression, patternReplacer);
        }
        return expression;
    }

    private Expression replaceValue(Expression targetExpression, Expression matchValue, List<KeyValue> resourceExpression, PatternReplacer patternReplacer) {
        Optional<Key> maybeKey = getKey(matchValue, resourceExpression);
        if(maybeKey.isPresent()) {
            targetExpression = patternReplacer.replace(targetExpression, matchValue, new Expression(maybeKey.get().getValue()));
        }
        return new Expression(targetExpression.getValue());
    }

    private Optional<Key> getKey(Expression expression, List<KeyValue> resourceExpression) {
        String value = expression.getValue();
        Key key = null;
        for (KeyValue resource : resourceExpression) {
            if(resource.getValue().getValue().equals(value)) {
                key = resource.getKey();
                break;
            }
        }
        return Optional.ofNullable(key);
    }


}
