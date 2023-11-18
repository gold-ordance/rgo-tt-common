package rgo.tt.common.persistence.sqlresult;

import rgo.tt.common.exceptions.PersistenceException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Optional<T> getEntity() {
        if (entities.isEmpty()) {
            return Optional.empty();
        }

        if (entities.size() > 1) {
            throw new PersistenceException("The number of entities is not equal to 1.");
        }

        return Optional.of(entities.get(0));
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
