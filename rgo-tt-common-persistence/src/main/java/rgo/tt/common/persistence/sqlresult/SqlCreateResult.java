package rgo.tt.common.persistence.sqlresult;

import java.util.Objects;

public final class SqlCreateResult<T> {

    private final T entity;

    private SqlCreateResult(T entity) {
        this.entity = entity;
    }

    public static <T> SqlCreateResult<T> from(T entity) {
        return new SqlCreateResult<>(entity);
    }

    public T getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlCreateResult<?> result = (SqlCreateResult<?>) o;
        return Objects.equals(entity, result.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public String toString() {
        return "SqlCreateResult{" +
                "entity=" + entity +
                '}';
    }
}
