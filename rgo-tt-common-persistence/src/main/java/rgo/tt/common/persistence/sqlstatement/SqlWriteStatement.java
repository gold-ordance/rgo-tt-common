package rgo.tt.common.persistence.sqlstatement;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Objects;

public final class SqlWriteStatement {

    private static final SqlKeyHolder DEFAULT_KEY;

    static {
        KeyHolder holder = new GeneratedKeyHolder();
        String[] keys = new String[]{"entity_id"};
        DEFAULT_KEY = new SqlKeyHolder(holder, keys);
    }

    private final SqlRequest request;
    private final SqlKeyHolder keyHolder;

    private SqlWriteStatement(SqlRequest request, SqlKeyHolder keyHolder) {
        this.request = request;
        this.keyHolder = keyHolder;
    }

    public static SqlWriteStatement from(SqlRequest request) {
        return new SqlWriteStatement(request, DEFAULT_KEY);
    }

    public static SqlWriteStatement from(SqlRequest request, SqlKeyHolder keyHolder) {
        return new SqlWriteStatement(request, keyHolder);
    }

    public SqlRequest getRequest() {
        return request;
    }

    public SqlKeyHolder getKeyHolder() {
        return keyHolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlWriteStatement that = (SqlWriteStatement) o;
        return Objects.equals(request, that.request)
                && Objects.equals(keyHolder, that.keyHolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, keyHolder);
    }

    @Override
    public String toString() {
        return "SqlWriteStatement{" +
                "request=" + request +
                ", keyHolder=" + keyHolder +
                '}';
    }
}
