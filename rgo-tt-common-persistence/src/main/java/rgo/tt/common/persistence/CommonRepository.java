package rgo.tt.common.persistence;

import rgo.tt.common.exceptions.PersistenceException;

import java.util.Optional;

public interface CommonRepository<T> {

    Optional<T> findByEntityId(Long entityId);

    default T getEntityById(Long entityId) {
        Optional<T> entity = findByEntityId(entityId);
        if (entity.isEmpty()) {
            throw new PersistenceException("The entity not found. entityId=" + entityId);
        }

        return entity.get();
    }
}
