package rgo.tt.common.persistence.sqlstatement.retry;

import rgo.tt.common.persistence.sqlstatement.SqlCreateStatement;

import java.util.Objects;

public final class RetryableSqlCreateStatement<T> {

    private final SqlCreateStatement<T> baseStatement;
    private final SqlRetryParameters parameters;

    private RetryableSqlCreateStatement(SqlCreateStatement<T> baseStatement, SqlRetryParameters parameters) {
        this.baseStatement = baseStatement;
        this.parameters = parameters;
    }

    public static <T> RetryableSqlCreateStatement<T> from(SqlCreateStatement<T> statement, SqlRetryParameters parameters) {
        return new RetryableSqlCreateStatement<>(statement, parameters);
    }

    public SqlCreateStatement<T> getBaseStatement() {
        return baseStatement;
    }

    public SqlRetryParameters getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetryableSqlCreateStatement<?> that = (RetryableSqlCreateStatement<?>) o;
        return Objects.equals(baseStatement, that.baseStatement)
                && Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseStatement, parameters);
    }

    @Override
    public String toString() {
        return "RetryableSqlCreateStatement{" +
                "baseStatement=" + baseStatement +
                ", parameters=" + parameters +
                '}';
    }
}
