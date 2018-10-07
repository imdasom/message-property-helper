package com.konai.generator.core;

import com.konai.common.core.Expression;

public interface KeyNameRule {
    String getKey(Expression expression);
}
