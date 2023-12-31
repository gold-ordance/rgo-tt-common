package rgo.tt.common.persistence.translator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.UniqueViolationException;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static rgo.tt.common.persistence.translator.ForeignKeyPostgresH2ExceptionHandler.H2_FK_CODE;
import static rgo.tt.common.persistence.translator.ForeignKeyPostgresH2ExceptionHandler.PG_FK_CODE;
import static rgo.tt.common.persistence.translator.UniqueViolationPostgresH2ExceptionHandler.UV_CODE;

class PostgresH2ExceptionTranslatorTest {

    private static final List<PostgresH2ExceptionHandler> HANDLERS = List.of(
            new ForeignKeyPostgresH2ExceptionHandler(),
            new UniqueViolationPostgresH2ExceptionHandler());

    private static final String STUB_STRING = "";

    private final PostgresH2ExceptionTranslator translator = new PostgresH2ExceptionTranslator(HANDLERS);

    @Test
    void doTranslate_noException() {
        SQLException exception = new SQLException(STUB_STRING, STUB_STRING);
        assertThatCode(() -> doTranslate(exception))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = { H2_FK_CODE, PG_FK_CODE })
    void doTranslate_pgForeignKey(String dbFkCode) {
        String keyId = "board_id";
        String errorMsg = "The boardId not found in the storage.";
        SQLException exception = new SQLException(keyId, dbFkCode);

        assertThatThrownBy(() -> doTranslate(exception))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage(errorMsg);
    }

    @Test
    void doTranslate_uniqueViolation() {
        String fieldName = "(email)";
        SQLException exception = new SQLException(fieldName, UV_CODE);
        assertThatThrownBy(() -> doTranslate(exception))
                .isInstanceOf(UniqueViolationException.class)
                .hasMessage("The email already exists.");
    }

    private void doTranslate(SQLException e) {
        translator.doTranslate(STUB_STRING, STUB_STRING, e);
    }
}
