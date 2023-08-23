package rgo.tt.common.persistence.sqlresult;

import java.util.List;
import java.util.Objects;

public final class SqlReadResult<T> {

    private final List<T> entities;

    private SqlReadResult(List<T> entities) {
        this.entities = entities;
    }

    public static <T> SqlReadResult<T> from(List<T> entities) {
        return new SqlReadResult<>(entities);
    }

    public List<T> getEntities() {
        return entities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlReadResult<?> that = (SqlReadResult<?>) o;
        return Objects.equals(entities, that.entities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entities);
    }

    @Override
    public String toString() {
        return "SqlReadResult{" +
                "entities=" + entities +
                '}';
    }
}
