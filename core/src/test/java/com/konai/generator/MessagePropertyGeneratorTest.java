package com.konai.generator;

import com.konai.common.core.Expression;
import com.konai.common.vo.Key;
import com.konai.common.vo.MessageProperty;
import com.konai.common.vo.Value;
import com.konai.generate.core.KeyNameRule;
import com.konai.generate.core.MessagePropertyGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagePropertyGeneratorTest {

    @Test
    public void generateSingle() {
        Expression expression = new Expression("상품명");
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", null);

        MessageProperty expectedMessageProperty = new MessageProperty(new Key("PROD_MANA_0001"), new Value("상품명"));

        MessagePropertyGenerator messagePropertyGenerator = new MessagePropertyGenerator();
        MessageProperty messageProperty = messagePropertyGenerator.generate(expression, keyNameRule);

        Assert.assertEquals(expectedMessageProperty, messageProperty);
    }

    @Test
    public void generate_counter테스트() {
        Map<String, String> map = new HashMap<>();
        map.put("PROD_MANA_0001", "서비스명");
        map.put("PROD_MANA_0003", "상품타입");

        Expression expression = new Expression("상품명");
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", map);

        MessageProperty expectedMessageProperty = new MessageProperty(new Key("PROD_MANA_0004"), new Value("상품명"));

        MessagePropertyGenerator messagePropertyGenerator = new MessagePropertyGenerator();
        MessageProperty messageProperty = messagePropertyGenerator.generate(expression, keyNameRule);

        Assert.assertEquals(expectedMessageProperty, messageProperty);
    }

    @Test
    public void generate_counter테스트_2() {
        Map<String, String> map = new HashMap<>();
        map.put("PROD_MANA_0001", "서비스명");
        map.put("PROD_MANA_0002", "상품타입");

        Expression expression = new Expression("상품명");
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", map);

        MessageProperty expectedMessageProperty = new MessageProperty(new Key("PROD_MANA_0003"), new Value("상품명"));

        MessagePropertyGenerator messagePropertyGenerator = new MessagePropertyGenerator();
        MessageProperty messageProperty = messagePropertyGenerator.generate(expression, keyNameRule);

        Assert.assertEquals(expectedMessageProperty, messageProperty);
    }

    @Test
    public void generateList() {
        List<Expression> expressions = new ArrayList<>();
        expressions.add(new Expression("상품명"));
        expressions.add(new Expression("상품타입"));
        expressions.add(new Expression("상품 명"));
        expressions.add(new Expression("서비스명"));
        KeyNameRule keyNameRule = new PortalKeyNameRule("PROD_MANA", "_", null);

        List<MessageProperty> expectedMessageProperties = new ArrayList<>();
        expectedMessageProperties.add(new MessageProperty(new Key("PROD_MANA_0001"), new Value("상품명")));
        expectedMessageProperties.add(new MessageProperty(new Key("PROD_MANA_0002"), new Value("상품타입")));
        expectedMessageProperties.add(new MessageProperty(new Key("PROD_MANA_0003"), new Value("상품 명")));
        expectedMessageProperties.add(new MessageProperty(new Key("PROD_MANA_0004"), new Value("서비스명")));

        MessagePropertyGenerator messagePropertyGenerator = new MessagePropertyGenerator();
        List<MessageProperty> messageProperties = messagePropertyGenerator.generate(expressions, keyNameRule);

        Assert.assertArrayEquals(expectedMessageProperties.toArray(), messageProperties.toArray());
    }
}
