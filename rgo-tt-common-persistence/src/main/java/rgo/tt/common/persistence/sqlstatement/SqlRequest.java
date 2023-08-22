package rgo.tt.common.persistence.sqlstatement;

import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Objects;

public final class SqlRequest {

    private final String query;
    private final SqlParameterSource params;

    public SqlRequest(String query) {
        this.query = query;
        this.params = EmptySqlParameterSource.INSTANCE;
    }

    public SqlRequest(String query, SqlParameterSource params) {
        this.query = query;
        this.params = params;
    }

    public String getQuery() {
        return query;
    }

    public SqlParameterSource getParams() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlRequest sqlRequest = (SqlRequest) o;
        return Objects.equals(query, sqlRequest.query)
                && Objects.equals(params, sqlRequest.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, params);
    }

    @Override
    public String toString() {
        return "SqlRequest{" +
                "query='" + query + '\'' +
                ", params=" + params +
                '}';
    }
}
