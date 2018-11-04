package com.konai.replace.core;

import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessagePropertyReplacer {

    public List<Expression> replace(List<MessageProperty> resourceExpression,
                                    List<Expression> targetExpressions,
                                    PatternSearcher<Expression, Expression> patternSearcher,
                                    PatternReplacer<Expression, Expression> patternReplacer) {
        List<Expression> afterReplaces = new ArrayList<>();
        for (Expression targetExpression : targetExpressions) {
            List<Expression> matchValues = patternSearcher.get(targetExpression);
            Expression afterExpression = replaceValues(resourceExpression, targetExpression, matchValues, patternReplacer);
            afterReplaces.add(afterExpression);
        }
        return afterReplaces;
    }

    private Expression replaceValues(List<MessageProperty> resourceExpression, Expression targetExpression, List<Expression> matchValues, PatternReplacer patternReplacer) {
        Expression expression = targetExpression;
        for(Expression matchValue : matchValues) {
            expression = replaceValue(expression, matchValue, resourceExpression, patternReplacer);
        }
        return expression;
    }

    private Expression replaceValue(Expression targetExpression, Expression matchValue, List<MessageProperty> resourceExpression, PatternReplacer<Expression, Expression> patternReplacer) {
        Optional<Key> maybeKey = getKey(matchValue, resourceExpression);
        if(maybeKey.isPresent()) {
            targetExpression = patternReplacer.replace(targetExpression, matchValue, new Expression(maybeKey.get().getValue()));
        }
        return new Expression(targetExpression.getValue());
    }

    private Optional<Key> getKey(Expression expression, List<MessageProperty> resourceExpression) {
        String value = expression.getValue();
        Key key = null;
        for (MessageProperty resource : resourceExpression) {
            if(resource.getValue().getValue().equals(value)) {
                key = resource.getKey();
                break;
            }
        }
        return Optional.ofNullable(key);
    }


}
