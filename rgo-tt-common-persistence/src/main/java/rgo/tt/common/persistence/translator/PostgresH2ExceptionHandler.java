package rgo.tt.common.persistence.translator;

import java.sql.SQLException;

public interface PostgresH2ExceptionHandler {

    void handle(SQLException exception);
}
