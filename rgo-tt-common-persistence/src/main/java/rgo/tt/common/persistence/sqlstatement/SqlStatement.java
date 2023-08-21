package rgo.tt.common.persistence.sqlstatement;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Arrays;
import java.util.Objects;

public final class SqlStatement<T> {

    private static final String[] DEFAULT_KEYS = new String[]{"entity_id"};

    private final String query;
    private final RowMapper<T> mapper;
    private final SqlParameterSource params;
    private final KeyHolder keyHolder;
    private final String[] keys;

    private SqlStatement(String query, RowMapper<T> mapper, SqlParameterSource params, String[] keys) {
        this.query = query;
        this.mapper = mapper;
        this.params = params;
        this.keyHolder = new GeneratedKeyHolder();
        this.keys = keys;
    }

    public static <T> SqlStatement<T> from(String query, RowMapper<T> mapper) {
        return new SqlStatement<>(
                query,
                mapper,
                EmptySqlParameterSource.INSTANCE,
                DEFAULT_KEYS);
    }

    public static <T> SqlStatement<T> from(String query, RowMapper<T> mapper, MapSqlParameterSource params) {
        return new SqlStatement<>(
                query,
                mapper,
                params,
                DEFAULT_KEYS);
    }

    public static <T> SqlStatement<T> from(String query, RowMapper<T> mapper, MapSqlParameterSource params, String[] keys) {
        return new SqlStatement<>(
                query,
                mapper,
                params,
                keys);
    }

    public String getQuery() {
        return query;
    }

    public RowMapper<T> getMapper() {
        return mapper;
    }

    public SqlParameterSource getParams() {
        return params;
    }

    public KeyHolder getKeyHolder() {
        return keyHolder;
    }

    public String[] getKeys() {
        return keys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlStatement<?> statement = (SqlStatement<?>) o;
        return Objects.equals(query, statement.query)
                && Objects.equals(mapper, statement.mapper)
                && Objects.equals(params, statement.params)
                && Objects.equals(keyHolder, statement.keyHolder)
                && Arrays.equals(keys, statement.keys);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(query, mapper, params, keyHolder);
        result = 31 * result + Arrays.hashCode(keys);
        return result;
    }

    @Override
    public String toString() {
        return "SqlStatement{" +
                "query='" + query + '\'' +
                ", mapper=" + mapper +
                ", params=" + params +
                ", keyHolder=" + keyHolder +
                ", keys=" + Arrays.toString(keys) +
                '}';
    }
}
