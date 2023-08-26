package rgo.tt.common.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.SmartDataSource;
import rgo.tt.common.exceptions.BaseException;
import rgo.tt.common.persistence.translator.PostgresH2ExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

public class DbTxManager extends AbstractDataSource implements SmartDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbTxManager.class);

    private final DataSource dataSource;
    private final StatementJdbcTemplateAdapter jdbc;
    private final ThreadLocal<ConnectionHolder> txConnection = new ThreadLocal<>();

    public DbTxManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbc = new StatementJdbcTemplateAdapter(new NamedParameterJdbcTemplate(nativeJdbc()));
    }

    private static BaseException exception(Exception e) {
        if (e instanceof BaseException) return (BaseException) e;
        return new BaseException("Tx failed.", e);
    }

    private JdbcTemplate nativeJdbc() {
        JdbcTemplate nativeJdbc = new JdbcTemplate(this);
        nativeJdbc.setExceptionTranslator(new PostgresH2ExceptionTranslator());
        return nativeJdbc;
    }

    public StatementJdbcTemplateAdapter jdbc() {
        return jdbc;
    }

    public void tx(Runnable runnable) {
        runAtomically(() -> {
            runnable.run();
            return null;
        });
    }

    public <T> T tx(Supplier<T> supplier) {
        return runAtomically(supplier);
    }

    private <T> T runAtomically(Supplier<T> supplier) {
        if (hasConnection()) {
            return supplier.get();
        }

        acquireConnection();

        try {
            T result = supplier.get();
            commit();
            return result;
        } catch (Exception e) {
            rollback();
            throw exception(e);
        } finally {
            releaseConnection();
        }
    }

    private void commit() throws SQLException {
        LOGGER.trace("Commit tx.");
        txConnection.get().connection.commit();
    }

    private boolean hasConnection() {
        return txConnection.get() != null;
    }

    private void acquireConnection() {
        LOGGER.trace("Start tx.");
        txConnection.set(new ConnectionHolder());
    }

    private void rollback() {
        try {
            LOGGER.trace("Rollback tx.");
            txConnection.get().connection.rollback();
        } catch (Exception e) {
            LOGGER.warn("Tx rollback failed.", e);
        }
    }

    private void releaseConnection() {
        try {
            txConnection.get().connection.close();
        } catch (Exception e) {
            LOGGER.warn("Connection release failed.", e);
        } finally {
            txConnection.remove();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (hasConnection()) {
            return txConnection.get().get();
        }
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    @Override
    public boolean shouldClose(Connection con) {
        if (!hasConnection()) return true;
        return txConnection.get().connection != con;
    }

    private class ConnectionHolder {

        private Connection connection;

        Connection get() throws SQLException {
            if (connection == null) {
                connection = dataSource.getConnection();
                LOGGER.trace("Acquire connection={}", connection);
                connection.setAutoCommit(false);
            }
            return connection;
        }

        @Override
        public String toString() {
            return "ConnectionHolder{connection=" + connection + '}';
        }
    }
}