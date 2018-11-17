package com.konai.generate.core;

import com.konai.common.core.Expression;
import com.konai.common.vo.Key;
import com.konai.common.vo.KeyValue;
import com.konai.common.vo.Value;

import java.util.ArrayList;
import java.util.List;

public class KeyValueGenerator {

    public static KeyValue generate(Expression expression, KeyNameRule keyNameRule) {
        Key key = new Key(keyNameRule.getKey(expression));
        Value value = new Value(expression.getValue());
        return new KeyValue(key, value);
    }

    public static List<KeyValue> generate(List<Expression> messageProperties, KeyNameRule keyNameRule) {
        List<KeyValue> newMessageProperties = new ArrayList<>();
        for (Expression expression : messageProperties) {
            newMessageProperties.add(generate(expression, keyNameRule));
        }
        return newMessageProperties;
    }

}
