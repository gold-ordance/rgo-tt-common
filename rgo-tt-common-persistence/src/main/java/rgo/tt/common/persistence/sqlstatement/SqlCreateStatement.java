package rgo.tt.common.persistence.sqlstatement;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import rgo.tt.common.persistence.function.FetchEntityById;

import java.util.Objects;

public final class SqlCreateStatement<T> {

    private static final SqlKeyHolder DEFAULT_KEY;

    static {
        KeyHolder holder = new GeneratedKeyHolder();
        String[] keys = new String[]{"entity_id"};
        DEFAULT_KEY = new SqlKeyHolder(holder, keys);
    }

    private final SqlRequest request;
    private final SqlKeyHolder keyHolder;
    private final FetchEntityById<T> function;

    private SqlCreateStatement(SqlRequest request, FetchEntityById<T> function, SqlKeyHolder keyHolder) {
        this.request = request;
        this.function = function;
        this.keyHolder = keyHolder;
    }

    public static <T> SqlCreateStatement<T> from(SqlRequest request, FetchEntityById<T> function) {
        return new SqlCreateStatement<>(request, function, DEFAULT_KEY);
    }

    public static <T> SqlCreateStatement<T> from(SqlRequest request, FetchEntityById<T> function, SqlKeyHolder keyHolder) {
        return new SqlCreateStatement<>(request, function, keyHolder);
    }

    public SqlRequest getRequest() {
        return request;
    }

    public SqlKeyHolder getKeyHolder() {
        return keyHolder;
    }

    public FetchEntityById<T> getFetchEntity() {
        return function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlCreateStatement<?> that = (SqlCreateStatement<?>) o;
        return Objects.equals(request, that.request)
                && Objects.equals(keyHolder, that.keyHolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, keyHolder);
    }

    @Override
    public String toString() {
        return "SqlCreateStatement{" +
                "request=" + request +
                ", keyHolder=" + keyHolder +
                '}';
    }
}
