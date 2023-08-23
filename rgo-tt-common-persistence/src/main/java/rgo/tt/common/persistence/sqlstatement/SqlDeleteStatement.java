package rgo.tt.common.persistence.sqlstatement;

import java.util.Objects;

public final class SqlDeleteStatement {

    private final SqlRequest request;

    private SqlDeleteStatement(SqlRequest request) {
        this.request = request;
    }

    public static SqlDeleteStatement from(SqlRequest request) {
        return new SqlDeleteStatement(request);
    }

    public SqlRequest getRequest() {
        return request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlDeleteStatement that = (SqlDeleteStatement) o;
        return Objects.equals(request, that.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request);
    }

    @Override
    public String toString() {
        return "SqlDeleteStatement{" +
                "request=" + request +
                '}';
    }
}
