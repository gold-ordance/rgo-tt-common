package rgo.tt.common.persistence.translator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rgo.tt.common.exceptions.InvalidEntityException;
import rgo.tt.common.exceptions.UniqueViolationException;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rgo.tt.common.persistence.translator.ForeignKeyPostgresH2ExceptionHandler.H2_FK_CODE;
import static rgo.tt.common.persistence.translator.ForeignKeyPostgresH2ExceptionHandler.PG_FK_CODE;
import static rgo.tt.common.persistence.translator.UniqueViolationPostgresH2ExceptionHandler.UV_CODE;
import static rgo.tt.common.utils.TestUtils.assertThrowsWithMessage;

class PostgresH2ExceptionTranslatorTest {

    private static final List<PostgresH2ExceptionHandler> HANDLERS = List.of(
            new ForeignKeyPostgresH2ExceptionHandler(),
            new UniqueViolationPostgresH2ExceptionHandler());

    private static final String STUB_STRING = "";

    private final PostgresH2ExceptionTranslator translator = new PostgresH2ExceptionTranslator(HANDLERS);

    @Test
    void doTranslate_noException() {
        SQLException exception = new SQLException(STUB_STRING, STUB_STRING);
        assertDoesNotThrow(() -> doTranslate(exception));
    }

    @ParameterizedTest
    @ValueSource(strings = { H2_FK_CODE, PG_FK_CODE })
    void doTranslate_pgForeignKey(String dbFkCode) {
        String keyId = "board_id";
        String errorMsg = "The boardId not found in the storage.";
        SQLException exception = new SQLException(keyId, dbFkCode);

        assertThrowsWithMessage(
                InvalidEntityException.class,
                () -> doTranslate(exception),
                errorMsg);
    }

    @Test
    void doTranslate_uniqueViolation() {
        SQLException exception = new SQLException(STUB_STRING, UV_CODE);
        assertThrows(UniqueViolationException.class, () -> doTranslate(exception));
    }

    private void doTranslate(SQLException e) {
        translator.doTranslate(STUB_STRING, STUB_STRING, e);
    }
}
