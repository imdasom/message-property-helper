package com.konai.generator;

import com.konai.common.domain.MessageProperty;
import com.konai.common.util.StringUtils;
import com.konai.generator.core.KeyNameRule;

public class PortalKeyNameRule implements KeyNameRule {

    private int counter = 1;
    private String prefix;
    private String delimeter;

    public PortalKeyNameRule(String prefix, String delimeter) {
        this.prefix = prefix;
        this.delimeter = delimeter;
    }

    @Override
    public String getKey(MessageProperty messageProperty) {
        String suffix = StringUtils.getZeroPaddingNumber(counter++, 4);
        return prefix + delimeter + suffix;
    }
}
