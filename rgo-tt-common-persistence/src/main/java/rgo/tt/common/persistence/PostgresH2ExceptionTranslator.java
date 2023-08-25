package rgo.tt.common.persistence;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import rgo.tt.common.exceptions.BaseException;
import rgo.tt.common.exceptions.InvalidEntityException;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostgresH2ExceptionTranslator extends SQLErrorCodeSQLExceptionTranslator {

    private static final String H2_FK_CODE = "23506";
    private static final String PG_FK_CODE = "23503";
    private static final List<String> FOREIGN_KEY_CODES = List.of(PG_FK_CODE, H2_FK_CODE);
    private static final String SUFFIX_ID = "_id";

    @Override
    protected DataAccessException doTranslate(String task, String sql, SQLException exception) {
        handleForeignKeyViolation(exception);
        return super.doTranslate(task, sql, exception);
    }

    private void handleForeignKeyViolation(SQLException exception) {
        if (isForeignKeyViolates(exception)) {
            String foreignKeyName = getForeignKeyName(exception);
            throw new InvalidEntityException("The " + foreignKeyName + " not found in the storage.");
        }
    }

    private boolean isForeignKeyViolates(SQLException exception) {
        return FOREIGN_KEY_CODES.contains(exception.getSQLState());
    }

    private String getForeignKeyName(SQLException exception) {
        String foreignKeyRegex = "(?i)(\\w+" + SUFFIX_ID + ")";
        Pattern pattern = Pattern.compile(foreignKeyRegex);
        Matcher matcher = pattern.matcher(exception.getMessage());
        return extractForeignKeyName(matcher);
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
