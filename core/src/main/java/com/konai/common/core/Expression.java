package com.konai.common.core;

import com.konai.common.util.StringUtils;

import java.util.Objects;

public class Expression {

    private String value;

    public Expression(String value) {
        if(value == null) {
            throw new IllegalArgumentException("value is null");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expression that = (Expression) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
