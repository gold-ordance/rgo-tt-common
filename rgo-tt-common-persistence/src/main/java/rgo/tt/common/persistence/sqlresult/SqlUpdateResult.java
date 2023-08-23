package rgo.tt.common.persistence.sqlresult;

import java.util.Objects;

public final class SqlUpdateResult<T> {

    private final T entity;

    private SqlUpdateResult(T entity) {
        this.entity = entity;
    }

    public static <T> SqlUpdateResult<T> from(T entity) {
        return new SqlUpdateResult<>(entity);
    }

    public T getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlUpdateResult<?> result = (SqlUpdateResult<?>) o;
        return Objects.equals(entity, result.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public String toString() {
        return "SqlUpdateResult{" +
                "entity=" + entity +
                '}';
    }
}
