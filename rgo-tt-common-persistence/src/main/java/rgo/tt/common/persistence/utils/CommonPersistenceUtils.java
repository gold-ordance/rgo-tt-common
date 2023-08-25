package rgo.tt.common.persistence.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.common.persistence.DbProperties;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public final class CommonPersistenceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonPersistenceUtils.class);

    private CommonPersistenceUtils() {
    }

    public static DataSource hikariSource(DbProperties dbProp) {
        HikariConfig hk = new HikariConfig();
        hk.setJdbcUrl(dbProp.getUrl());
        hk.setSchema(dbProp.getSchema());
        hk.setUsername(dbProp.getUsername());
        hk.setPassword(dbProp.getPassword());
        hk.setMaximumPoolSize(dbProp.getMaxPoolSize());
        hk.setAutoCommit(false);
        return new HikariDataSource(hk);
    }

    public static <T> Optional<T> getFirstEntity(List<T> entities) {
        if (entities.isEmpty()) {
            LOGGER.info("The entity not found.");
            return Optional.empty();
        }

        if (entities.size() > 1) {
            throw new PersistenceException("The number of entities is not equal to 1.");
        }

        return Optional.of(entities.get(0));
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
