package rgo.tt.common.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import rgo.tt.common.persistence.sqlstatement.SqlStatement;

import java.util.List;

public class StatementJdbcTemplateAdapter {

    private final NamedParameterJdbcTemplate jdbc;

    public StatementJdbcTemplateAdapter(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public <T> List<T> query(SqlStatement<T> ss) {
        return jdbc.query(ss.getQuery(), ss.getParams(), ss.getMapper());
    }

    public <T> int save(SqlStatement<T> ss) {
        return jdbc.update(ss.getQuery(), ss.getParams(), ss.getKeyHolder(), ss.getKeys());
    }

    public <T> int update(SqlStatement<T> ss) {
        return jdbc.update(ss.getQuery(), ss.getParams());
    }
}
