package rgo.tt.common.persistence.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.common.persistence.DbProperties;

import javax.sql.DataSource;

public final class CommonPersistenceUtils {

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

    public static void validateSaveResult(Number key) {
        if (key == null) {
            throw new PersistenceException("The entity save error.");
        }
    }

    public static void validateUpdateResult(int result) {
        if (result == 0) {
            throw new InvalidEntityException("The entityId not found in the storage.");
        }
    }
}
