package rgo.tt.common.persistence.sqlstatement;

import org.springframework.jdbc.support.KeyHolder;

import java.util.Arrays;
import java.util.Objects;

public final class SqlKeyHolder {

    private final KeyHolder keyHolder;
    private final String[] keys;

    public SqlKeyHolder(KeyHolder keyHolder, String[] keys) {
        this.keyHolder = keyHolder;
        this.keys = keys;
    }

    public KeyHolder getKeyHolder() {
        return keyHolder;
    }

    public Number getKey() {
        return keyHolder.getKey();
    }

    public String[] getKeys() {
        return keys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlKeyHolder that = (SqlKeyHolder) o;
        return Objects.equals(keyHolder, that.keyHolder)
                && Arrays.equals(keys, that.keys);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(keyHolder);
        result = 31 * result + Arrays.hashCode(keys);
        return result;
    }

    @Override
    public String toString() {
        return "SqlKeyHolder{" +
                "keyHolder=" + keyHolder +
                ", keys=" + Arrays.toString(keys) +
                '}';
    }
}
