package com.konai.generate.core;

import com.konai.common.core.Expression;

public interface KeyNameRule {
    String getKey(Expression expression);
}
