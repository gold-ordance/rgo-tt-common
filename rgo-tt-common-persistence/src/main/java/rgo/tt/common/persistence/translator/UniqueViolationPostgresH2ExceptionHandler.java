package rgo.tt.common.persistence.translator;

import rgo.tt.common.exceptions.UniqueViolationException;

import java.sql.SQLException;

public class UniqueViolationPostgresH2ExceptionHandler implements PostgresH2ExceptionHandler {

    private static final String UV_CODE = "23505";

    @Override
    public void handle(SQLException exception) {
        if (isUniqueViolation(exception)) {
            throw new UniqueViolationException(exception);
        }
    }

    private boolean isUniqueViolation(SQLException exception) {
        return exception.getSQLState().equals(UV_CODE);
    }
}
