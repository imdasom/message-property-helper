package com.konai.collect;

import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;

import java.util.ArrayList;
import java.util.List;

public class KeyPatternSearcher implements PatternSearcher<Expression, Expression> {

    @Override
    public List<Expression> get(Expression source) {
        List<Expression> results = new ArrayList<>();
        String sourceToString = source.getValue();
        if (sourceToString.startsWith("#")) {
            results.add(new Expression(sourceToString.replaceAll("[#]|[{]|[}]", "")));
        }
        return results;
    }
}
