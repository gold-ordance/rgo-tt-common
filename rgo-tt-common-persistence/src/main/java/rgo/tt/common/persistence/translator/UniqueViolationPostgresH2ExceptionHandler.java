package rgo.tt.common.persistence.translator;

import com.google.common.annotations.VisibleForTesting;
import rgo.tt.common.exceptions.UniqueViolationException;

import java.sql.SQLException;

public class UniqueViolationPostgresH2ExceptionHandler implements PostgresH2ExceptionHandler {

    @VisibleForTesting
    static final String UV_CODE = "23505";

    @Override
    public void handle(SQLException exception) {
        if (isUniqueViolation(exception)) {
            throw new UniqueViolationException(exception);
        }
    }

    private boolean isUniqueViolation(SQLException exception) {
        return UV_CODE.equals(exception.getSQLState());
    }
}
