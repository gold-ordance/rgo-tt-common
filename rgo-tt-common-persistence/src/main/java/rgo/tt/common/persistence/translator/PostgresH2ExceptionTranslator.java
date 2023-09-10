package rgo.tt.common.persistence.translator;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.List;

public class PostgresH2ExceptionTranslator extends SQLErrorCodeSQLExceptionTranslator {

    private final List<PostgresH2ExceptionHandler> exceptionHandlers;

    public PostgresH2ExceptionTranslator(List<PostgresH2ExceptionHandler> handlers) {
        exceptionHandlers = handlers;
    }

    @Override
    protected DataAccessException doTranslate(@Nonnull String task, String sql, @Nonnull SQLException exception) {
        handleException(exception);
        return super.doTranslate(task, sql, exception);
    }

    private void handleException(SQLException exception) {
        exceptionHandlers.forEach(h -> h.handle(exception));
    }
}
