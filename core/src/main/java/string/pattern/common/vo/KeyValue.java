package string.pattern.common.vo;

import java.util.Objects;

public class KeyValue {
    private Key key;
    private Value value;

    public KeyValue(Key key, Value value) {
        if(key == null || value == null) {
            throw new NullPointerException("Key or Value is null. Key:"+key+", Value:"+value);
        }
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyValue that = (KeyValue) o;
        return Objects.equals(key.getValue(), that.key.getValue()) &&
                Objects.equals(value.getValue(), that.value.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(key.getValue(), value.getValue());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KeyValue{");
        sb.append("key=").append(key.getValue());
        sb.append(", value=").append(value.getValue());
        sb.append('}');
        return sb.toString();
    }
}
