package rgo.tt.common.persistence.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.PersistenceException;

import java.util.List;
import java.util.Optional;

public final class CommonPersistenceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonPersistenceUtils.class);

    private CommonPersistenceUtils() {
    }

    public static <T> Optional<T> getFirstElement(List<T> list) {
        if (list.isEmpty()) {
            LOGGER.info("The entity not found.");
            return Optional.empty();
        }

        if (list.size() > 1) {
            throw new PersistenceException("The number of entities is not equal to 1.");
        }

        return Optional.of(list.get(0));
    }

    public static void validateSaveResult(int result, Number key) {
        if (result != 1 || key == null) {
            throw new PersistenceException("The entity save error.");
        }
    }

    public static void validateUpdateResult(int result) {
        if (result == 0) {
            throw new InvalidEntityException("The entityId not found in the storage.");
        }
    }
}
