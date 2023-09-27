package rgo.tt.common.persistence.translator;

import rgo.tt.common.exceptions.BaseException;
import rgo.tt.common.exceptions.InvalidEntityException;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForeignKeyPostgresH2ExceptionHandler implements PostgresH2ExceptionHandler {

    static final String H2_FK_CODE = "23506";
    static final String PG_FK_CODE = "23503";

    private static final List<String> FOREIGN_KEY_CODES = List.of(PG_FK_CODE, H2_FK_CODE);
    private static final String SUFFIX_ID = "_id";

    @Override
    public void handle(SQLException exception) {
        if (isForeignKeyViolates(exception)) {
            throwForeignKeyViolationException(exception);
        }
    }

    private boolean isForeignKeyViolates(SQLException exception) {
        return FOREIGN_KEY_CODES.contains(exception.getSQLState());
    }

    private void throwForeignKeyViolationException(SQLException exception) {
        String foreignKeyName = getForeignKeyName(exception);
        throw new InvalidEntityException("The " + foreignKeyName + " not found in the storage.");
    }

    private String getForeignKeyName(SQLException exception) {
        Matcher matcher = createForeignKeyMatcher(exception);
        return extractForeignKeyName(matcher);
    }

    private static Matcher createForeignKeyMatcher(SQLException exception) {
        String foreignKeyRegex = "(?i)(\\w+" + SUFFIX_ID + ")";
        Pattern pattern = Pattern.compile(foreignKeyRegex);
        return pattern.matcher(exception.getMessage());
    }

    private String extractForeignKeyName(Matcher matcher) {
        if (matcher.find()) {
            String keyName = matcher.group();
            return convertToCamelCase(keyName);
        }

        throw new BaseException("The name of the foreign key could not be found in the error message.");
    }

    private String convertToCamelCase(String keyName) {
        return keyName.toLowerCase()
                .replace(SUFFIX_ID, "Id");
    }
}
