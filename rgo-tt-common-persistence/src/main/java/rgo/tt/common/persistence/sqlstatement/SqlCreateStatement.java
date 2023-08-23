package rgo.tt.common.persistence.sqlstatement;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Objects;
import java.util.function.LongFunction;

public final class SqlCreateStatement<T> {

    private static final SqlKeyHolder DEFAULT_KEY;

    static {
        KeyHolder holder = new GeneratedKeyHolder();
        String[] keys = new String[]{"entity_id"};
        DEFAULT_KEY = new SqlKeyHolder(holder, keys);
    }

    private final SqlRequest request;
    private final SqlKeyHolder keyHolder;
    private final LongFunction<T> fetchEntity;

    private SqlCreateStatement(SqlRequest request, LongFunction<T> fetchEntity, SqlKeyHolder keyHolder) {
        this.request = request;
        this.fetchEntity = fetchEntity;
        this.keyHolder = keyHolder;
    }

    public static <T> SqlCreateStatement<T> from(SqlRequest request, LongFunction<T> fetchEntity) {
        return new SqlCreateStatement<>(request, fetchEntity, DEFAULT_KEY);
    }

    public static <T> SqlCreateStatement<T> from(SqlRequest request, LongFunction<T> fetchEntity, SqlKeyHolder keyHolder) {
        return new SqlCreateStatement<>(request, fetchEntity, keyHolder);
    }

    public SqlRequest getRequest() {
        return request;
    }

    public SqlKeyHolder getKeyHolder() {
        return keyHolder;
    }

    public LongFunction<T> getFetchEntity() {
        return fetchEntity;
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
