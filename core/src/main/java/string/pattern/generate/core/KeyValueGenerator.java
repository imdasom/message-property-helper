package string.pattern.generate.core;

import string.pattern.common.core.Expression;
import string.pattern.common.vo.Key;
import string.pattern.common.vo.KeyValue;
import string.pattern.common.vo.Value;

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
