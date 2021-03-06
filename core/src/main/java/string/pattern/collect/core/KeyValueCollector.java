package string.pattern.collect.core;

import string.pattern.common.core.Expression;
import string.pattern.common.core.PatternSearcher;

import java.util.ArrayList;
import java.util.List;

public class KeyValueCollector {

    private static KeyValueCollector collector;

    private KeyValueCollector() {}

    public static KeyValueCollector getInstance() {
        if(collector == null) {
            collector = new KeyValueCollector();
        }
        return collector;
    }

    public List<Expression> collect(List<Expression> sources, PatternSearcher patternSearcher) {
        List<Expression> results = new ArrayList<>();
        for(Expression inputValue : sources) {
            List<Expression> matchResults = patternSearcher.get(inputValue);
            results.addAll(matchResults);
        }
        return results;
    }
}
