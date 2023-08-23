package rgo.tt.common.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

import java.util.List;

import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.validateSaveResult;
import static rgo.tt.common.persistence.utils.CommonPersistenceUtils.validateUpdateResult;

public class StatementJdbcTemplateAdapter {

    private final NamedParameterJdbcTemplate jdbc;

    public StatementJdbcTemplateAdapter(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
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

    private <T> T execute(SqlCreateStatement<T> statement) {
        SqlRequest request = statement.getRequest();
        SqlKeyHolder holder = statement.getKeyHolder();
        int result = jdbc.update(request.getQuery(), request.getParams(), holder.getKeyHolder(), holder.getKeys());
        Number key = statement.getKeyHolder().getKey();
        validateSaveResult(result, key);
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
