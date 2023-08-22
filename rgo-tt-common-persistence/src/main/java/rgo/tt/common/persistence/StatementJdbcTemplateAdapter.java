package rgo.tt.common.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import rgo.tt.common.persistence.sqlstatement.SqlKeyHolder;
import rgo.tt.common.persistence.sqlstatement.SqlRequest;
import rgo.tt.common.persistence.sqlstatement.SqlReadStatement;
import rgo.tt.common.persistence.sqlstatement.SqlWriteStatement;

import java.util.List;

public class StatementJdbcTemplateAdapter {

    private final NamedParameterJdbcTemplate jdbc;

    public StatementJdbcTemplateAdapter(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public <T> List<T> query(SqlReadStatement<T> statement) {
        SqlRequest request = statement.getRequest();
        return jdbc.query(request.getQuery(), request.getParams(), statement.getMapper());
    }

    public int save(SqlWriteStatement statement) {
        SqlRequest request = statement.getRequest();
        SqlKeyHolder holder = statement.getKeyHolder();

        return jdbc.update(
                request.getQuery(),
                request.getParams(),
                holder.getKeyHolder(),
                holder.getKeys());
    }

    public int update(SqlWriteStatement statement) {
        SqlRequest request = statement.getRequest();
        return jdbc.update(request.getQuery(), request.getParams());
    }
}
