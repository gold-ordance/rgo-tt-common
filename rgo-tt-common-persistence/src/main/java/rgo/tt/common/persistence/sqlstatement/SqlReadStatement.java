package rgo.tt.common.persistence.sqlstatement;

import org.springframework.jdbc.core.RowMapper;

import java.util.Objects;

public class SqlReadStatement<T> {

    private final SqlRequest request;
    private final RowMapper<T> mapper;

    private SqlReadStatement(SqlRequest request, RowMapper<T> mapper) {
        this.request = request;
        this.mapper = mapper;
    }

    public static <T> SqlReadStatement<T> from(SqlRequest query, RowMapper<T> mapper) {
        return new SqlReadStatement<>(query, mapper);
    }

    public SqlRequest getRequest() {
        return request;
    }

    public RowMapper<T> getMapper() {
        return mapper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlReadStatement<?> that = (SqlReadStatement<?>) o;
        return Objects.equals(request, that.request)
                && Objects.equals(mapper, that.mapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, mapper);
    }

    @Override
    public String toString() {
        return "SqlReadStatement{" +
                "request=" + request +
                ", mapper=" + mapper +
                '}';
    }
}
