package rgo.tt.common.persistence.translator;

import com.google.common.annotations.VisibleForTesting;
import rgo.tt.common.exceptions.BaseException;
import rgo.tt.common.exceptions.UniqueViolationException;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueViolationPostgresH2ExceptionHandler implements PostgresH2ExceptionHandler {

    @VisibleForTesting
    static final String UV_CODE = "23505";

    @Override
    public void handle(SQLException exception) {
        if (isUniqueViolation(exception)) {
            throwUniqueViolationException(exception);
        }
    }

    private boolean isUniqueViolation(SQLException exception) {
        return UV_CODE.equals(exception.getSQLState());
    }

    private void throwUniqueViolationException(SQLException exception) {
        String fieldName = getFieldName(exception);
        throw new UniqueViolationException("The " + fieldName + " already exists.");
    }

    private String getFieldName(SQLException exception) {
        Matcher matcher = createFieldNameMatcher(exception);
        return extractForeignKeyName(matcher);
    }

    private static Matcher createFieldNameMatcher(SQLException exception) {
        String fieldNameRegex = "\\((.*?)\\)";
        Pattern pattern = Pattern.compile(fieldNameRegex);
        return pattern.matcher(exception.getMessage());
    }

    private String extractForeignKeyName(Matcher matcher) {
        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new BaseException("The field name could not be found in the error message.");
    }
}
