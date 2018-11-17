package com.konai.replace.core;

import com.konai.common.core.Expression;

public interface PatternReplacer {
     Expression replace(Expression destination, Expression target, Expression replacement);
}
