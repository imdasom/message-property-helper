package com.konai.collect.core;

import java.util.ArrayList;
import java.util.List;

public class MessagePropertyCollector<INPUT_TYPE, OUTPUT_TYPE> {

    private static MessagePropertyCollector collector;

    private MessagePropertyCollector() {}

    public static MessagePropertyCollector getInstance() {
        if(collector == null) {
            collector = new MessagePropertyCollector();
        }
        return collector;
    }

    public List<OUTPUT_TYPE> collect(List<INPUT_TYPE> sources, PatternSearcher<INPUT_TYPE, OUTPUT_TYPE> patternSearcher) {
        List<OUTPUT_TYPE> results = new ArrayList<>();
        for(INPUT_TYPE inputValue : sources) {
            List<OUTPUT_TYPE> matchResults = patternSearcher.get(inputValue);
            results.addAll(matchResults);
        }
        return results;
    }
}
