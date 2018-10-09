package com.konai.collect;

import com.konai.collect.core.MessagePropertyCollector;
import com.konai.collect.core.PatternSearcher;
import com.konai.common.core.Expression;
import com.konai.common.util.FileUtils;
import com.konai.core.ThymeleafTextPatternSearcher;
import com.konai.core.ValuePatternSearcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessagePropertyCollectorTest {

    private List<Expression> sourceExpressions;
    private MessagePropertyCollector<Expression, Expression> collector;
    private PatternSearcher<Expression, Expression> patternSearcher;
    private PatternSearcher<Expression, Expression> keyPatternSearcher;
    private PatternSearcher<Expression, Expression> valuePatternSearcher;

    @Before
    public void init() throws IOException {
        File file = new File(".\\test\\resources\\html\\productView.html");
        InputStream inputStream = new FileInputStream(file);
        List<String> sources = FileUtils.readLines(inputStream);
        sourceExpressions = sources.stream().map(Expression::new).collect(Collectors.toList());
        collector = new MessagePropertyCollector();
        patternSearcher = new ThymeleafTextPatternSearcher();
        keyPatternSearcher = new KeyPatternSearcher();
        valuePatternSearcher = new ValuePatternSearcher();
    }

    @Test
    public void collectThymeleafExpressions() {
        List<Expression> expectedExpressions = getThyemleafExpressions();
        List<Expression> thymeleafExpressions = collector.collect(sourceExpressions, patternSearcher);
        Assert.assertArrayEquals(expectedExpressions.toArray(), thymeleafExpressions.toArray());
    }

    @Test
    public void collectKeyExpressions() {
        List<Expression> thymeleafExpressions = getThyemleafExpressions();
        List<Expression> expectedExpressions = new ArrayList<>();
        expectedExpressions.add(new Expression("PROD_MANA_0006"));
        expectedExpressions.add(new Expression("PROD_MANA_0010"));
        List<Expression> keyExpressions = collector.collect(thymeleafExpressions, keyPatternSearcher);
        Assert.assertArrayEquals(expectedExpressions.toArray(), keyExpressions.toArray());
    }

    @Test
    public void collectValueExpressions() {
        List<Expression> thymeleafExpressions = getThyemleafExpressions();
        List<Expression> expectedExpressions = new ArrayList<>();
        expectedExpressions.add(new Expression("상품명"));
        expectedExpressions.add(new Expression("상품타입"));
        expectedExpressions.add(new Expression("상품 명"));
        expectedExpressions.add(new Expression("서비스명"));
        List<Expression> valueExpressions = collector.collect(thymeleafExpressions, valuePatternSearcher);
        Assert.assertArrayEquals(expectedExpressions.toArray(), valueExpressions.toArray());
    }

    private List<Expression> getThyemleafExpressions() {
        List<Expression> thymeleafExpressions = new ArrayList<>();
        thymeleafExpressions.addAll(getKeyExpressions());
        thymeleafExpressions.addAll(getValueExpressions());
        return thymeleafExpressions;
    }

    private List<Expression> getKeyExpressions() {
        List<Expression> thymeleafExpressions = new ArrayList<>();
        thymeleafExpressions.add(new Expression("#{PROD_MANA_0006}"));
        thymeleafExpressions.add(new Expression("#{PROD_MANA_0010}"));
        return thymeleafExpressions;
    }

    private List<Expression> getValueExpressions() {
        List<Expression> thymeleafExpressions = new ArrayList<>();
        thymeleafExpressions.add(new Expression("'상품명'"));
        thymeleafExpressions.add(new Expression("'상품타입'"));
        thymeleafExpressions.add(new Expression("'상품 명'"));
        thymeleafExpressions.add(new Expression("'서비스명'"));
        return thymeleafExpressions;
    }
}
