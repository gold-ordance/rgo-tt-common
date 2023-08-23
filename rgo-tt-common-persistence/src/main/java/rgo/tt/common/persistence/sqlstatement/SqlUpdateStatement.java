package rgo.tt.common.persistence.sqlstatement;

import rgo.tt.common.persistence.function.FetchEntity;

import java.util.Objects;

public final class SqlUpdateStatement<T> {

    private final SqlRequest request;
    private final FetchEntity<T> function;

    private SqlUpdateStatement(SqlRequest request, FetchEntity<T> function) {
        this.request = request;
        this.function = function;
    }

    public static <T> SqlUpdateStatement<T> from(SqlRequest request, FetchEntity<T> function) {
        return new SqlUpdateStatement<>(request, function);
    }

    public SqlRequest getRequest() {
        return request;
    }

    public FetchEntity<T> getFetchEntity() {
        return function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlUpdateStatement<?> that = (SqlUpdateStatement<?>) o;
        return Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request);
    }

    @Override
    public String toString() {
        return "SqlUpdateStatement{" +
                "request=" + request +
                '}';
    }
}
