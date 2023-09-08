package rgo.tt.common.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import rgo.tt.common.exceptions.PersistenceException;
import rgo.tt.common.persistence.function.FetchEntity;
import rgo.tt.common.persistence.function.FetchEntityById;
import rgo.tt.common.persistence.sqlresult.SqlCreateResult;
import rgo.tt.common.persistence.sqlresult.SqlDeleteResult;
import rgo.tt.common.persistence.sqlresult.SqlReadResult;
import rgo.tt.common.persistence.sqlresult.SqlUpdateResult;
import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.SqlDeleteStatement;
import rgo.tt.common.persistence.sqlstatement.SqlKeyHolder;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;
import rgo.tt.common.persistence.sqlstatement.SqlUpdateStatement;
import rgo.tt.common.persistence.sqlstatement.retry.RetryableSqlCreateStatement;
import rgo.tt.common.persistence.sqlstatement.retry.SqlRetryParameters;
import rgo.tt.common.persistence.translator.PostgresH2ExceptionHandler;
import rgo.tt.common.persistence.translator.PostgresH2ExceptionTranslator;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.validateSaveResult;
import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.validateUpdateResult;

public class StatementJdbcTemplateAdapter {

    private final NamedParameterJdbcTemplate jdbc;

    public StatementJdbcTemplateAdapter(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public StatementJdbcTemplateAdapter(DataSource dataSource, List<PostgresH2ExceptionHandler> handlers) {
        JdbcTemplate nativeJdbc = nativeJdbc(dataSource, handlers);
        this.jdbc = new NamedParameterJdbcTemplate(nativeJdbc);
    }

    private JdbcTemplate nativeJdbc(DataSource dataSource, List<PostgresH2ExceptionHandler> handlers) {
        JdbcTemplate nativeJdbc = new JdbcTemplate(dataSource);
        nativeJdbc.setExceptionTranslator(new PostgresH2ExceptionTranslator(handlers));
        return nativeJdbc;
    }

    public <T> SqlReadResult<T> read(SqlReadStatement<T> statement) {
        List<T> entities = execute(statement);
        return SqlReadResult.from(entities);
    }

    private <T> List<T> execute(SqlReadStatement<T> statement) {
        SqlRequest request = statement.getRequest();
        return jdbc.query(request.getQuery(), request.getParams(), statement.getMapper());
    }

    public <T> SqlCreateResult<T> save(SqlCreateStatement<T> statement) {
        T entity = execute(statement);
        return SqlCreateResult.from(entity);
    }

    public <T> SqlCreateResult<T> save(RetryableSqlCreateStatement<T> retryable) {
        SqlRetryParameters params = retryable.getParameters();
        Set<Exception> exceptions = new HashSet<>();
        int attempts = 0;

        while (attempts != params.getAttempts()) {
            try {
                T entity = execute(retryable.getBaseStatement());
                return SqlCreateResult.from(entity);
            } catch (Exception e) {
                exceptions.add(e);
                attempts++;
            }
        }

        throw new PersistenceException("Failed to execute query after " + attempts + " attempts. Reason: " + exceptions);
    }

    private <T> T execute(SqlCreateStatement<T> statement) {
        SqlRequest request = statement.getRequest();
        SqlKeyHolder holder = statement.getKeyHolder();
        jdbc.update(request.getQuery(), request.getParams(), holder.getKeyHolder(), holder.getKeys());
        Number key = statement.getKeyHolder().getKey();
        validateSaveResult(key);
        FetchEntityById<T> function = statement.getFetchEntity();
        return function.fetch(key.longValue());
    }

    public <T> SqlUpdateResult<T> update(SqlUpdateStatement<T> statement) {
        T entity = execute(statement);
        return SqlUpdateResult.from(entity);
    }

    private <T> T execute(SqlUpdateStatement<T> statement) {
        SqlRequest request = statement.getRequest();
        int result = jdbc.update(request.getQuery(), request.getParams());
        validateUpdateResult(result);
        FetchEntity<T> function = statement.getFetchEntity();
        return function.fetch();
    }

    public SqlDeleteResult delete(SqlDeleteStatement statement) {
        boolean isDeleted = execute(statement);
        return SqlDeleteResult.from(isDeleted);
    }

    private boolean execute(SqlDeleteStatement statement) {
        SqlRequest request = statement.getRequest();
        int result = jdbc.update(request.getQuery(), request.getParams());
        return result == 1;
    }
}
