package com.konai.generator.core;

import com.konai.common.domain.MessageProperty;

public interface KeyNameRule {
    String getKey(MessageProperty messageProperty);
}
