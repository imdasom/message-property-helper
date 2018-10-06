package com.konai.generator.core;

import com.konai.common.domain.Key;
import com.konai.common.domain.MessageProperty;
import com.konai.common.domain.Value;

import java.util.ArrayList;
import java.util.List;

public class MessagePropertyGenerator {

    private KeyNameRule keyNameRule;

    public MessagePropertyGenerator(KeyNameRule keyNameRule) {
        this.keyNameRule = keyNameRule;
    }

    public List<MessageProperty> generate(List<MessageProperty> messageProperties) {
        List<MessageProperty> newMessageProperties = new ArrayList<>();
        for (MessageProperty messageProperty : messageProperties) {
            Key key = new Key(keyNameRule.getKey(messageProperty));
            Value value = messageProperty.getValue();
            newMessageProperties.add(new MessageProperty(key, value));
        }
        return newMessageProperties;
    }

}
