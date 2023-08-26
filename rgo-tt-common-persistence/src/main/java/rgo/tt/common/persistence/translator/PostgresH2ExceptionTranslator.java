package rgo.tt.common.persistence.translator;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;
import java.util.List;

public class PostgresH2ExceptionTranslator extends SQLErrorCodeSQLExceptionTranslator {

    private static final List<PostgresH2ExceptionHandler> EXCEPTION_HANDLERS = List.of(
            new ForeignKeyPostgresH2ExceptionHandler());

    @Override
    protected DataAccessException doTranslate(String task, String sql, SQLException exception) {
        handleException(exception);
        return super.doTranslate(task, sql, exception);
    }

    private void handleException(SQLException exception) {
        EXCEPTION_HANDLERS.forEach(h -> h.handle(exception));
    }
}
