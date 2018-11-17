package com.konai.common.core;

public interface PatternReplacer {
     Expression replace(Expression destination, Expression target, Expression replacement);
}
