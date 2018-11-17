package com.konai.collect.core;

import com.konai.common.core.Expression;

import java.util.List;

public interface PatternSearcher {

    List<Expression> get(Expression source);
}
