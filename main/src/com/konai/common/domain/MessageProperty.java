package com.konai.common.domain;

import java.util.Objects;

public class MessageProperty {
    private Key key;
    private Value value;

    public MessageProperty(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public MessageProperty(Value value) {
        this.key = null;
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageProperty that = (MessageProperty) o;
        return Objects.equals(key.getValue(), that.key.getValue()) &&
                Objects.equals(value.getValue(), that.value.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(key.getValue(), value.getValue());
    }
}
