package com.konai.generator.core;

import com.konai.common.core.Expression;
import com.konai.common.valueobject.Key;
import com.konai.common.valueobject.MessageProperty;
import com.konai.common.valueobject.Value;

import java.util.ArrayList;
import java.util.List;

public class MessagePropertyGenerator {

    public MessageProperty generate(Expression expression, KeyNameRule keyNameRule) {
        Key key = new Key(keyNameRule.getKey(expression));
        Value value = new Value(expression.getValue());
        return new MessageProperty(key, value);
    }

    public List<MessageProperty> generate(List<Expression> messageProperties, KeyNameRule keyNameRule) {
        List<MessageProperty> newMessageProperties = new ArrayList<>();
        for (Expression expression : messageProperties) {
            newMessageProperties.add(generate(expression, keyNameRule));
        }
        return newMessageProperties;
    }

}
