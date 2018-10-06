package com.konai.generator;

import com.konai.common.domain.Key;
import com.konai.common.domain.MessageProperty;
import com.konai.common.domain.Value;
import com.konai.generator.core.KeyNameRule;
import com.konai.generator.core.MessagePropertyGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MessagePropertyGeneratorTest {

    @Test
    public void generate() {
        List<MessageProperty> newMessageProperty = new ArrayList<>();
        newMessageProperty.add(new MessageProperty(new Key("MEMBER_0001"), new Value("안녕하세요")));
        newMessageProperty.add(new MessageProperty(new Key("MEMBER_0002"), new Value("키보드")));
        newMessageProperty.add(new MessageProperty(new Key("MEMBER_0003"), new Value("마우스")));

        KeyNameRule keyNameRule = new PortalKeyNameRule("MEMBER", "_");
        MessagePropertyGenerator messagePropertyGenerator = new MessagePropertyGenerator(keyNameRule);
        List<MessageProperty> generatedResult = messagePropertyGenerator.generate(newMessageProperty);
        Assert.assertArrayEquals(newMessageProperty.toArray(), generatedResult.toArray());
    }
}
