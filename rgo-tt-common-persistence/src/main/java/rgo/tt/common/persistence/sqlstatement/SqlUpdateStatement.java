package rgo.tt.common.persistence.sqlstatement;

import java.util.Objects;
import java.util.function.Supplier;

public final class SqlUpdateStatement<T> {

    private final SqlRequest request;
    private final Supplier<T> fetchEntity;

    private SqlUpdateStatement(SqlRequest request, Supplier<T> fetchEntity) {
        this.request = request;
        this.fetchEntity = fetchEntity;
    }

    public static <T> SqlUpdateStatement<T> from(SqlRequest request, Supplier<T> fetchEntity) {
        return new SqlUpdateStatement<>(request, fetchEntity);
    }

    public SqlRequest getRequest() {
        return request;
    }

    public Supplier<T> getFetchEntity() {
        return fetchEntity;
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
