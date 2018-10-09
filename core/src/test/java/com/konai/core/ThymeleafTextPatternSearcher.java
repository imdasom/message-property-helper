package com.konai.core;

import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThymeleafTextPatternSearcher implements PatternSearcher<Expression, Expression> {

    @Override
    public List<Expression> get(Expression source) {
        List<Expression> expresisons = new ArrayList<>();
        Pattern thymeleafTextExpression = Pattern.compile("(th:text=\"){1}(.*?){1}(\"){1}");
        Matcher matcher = thymeleafTextExpression.matcher(source.getValue());
        while(matcher.find()) {
            String message = matcher.group(2);
            expresisons.add(new Expression(message));
        }
        return expresisons;
    }
}
