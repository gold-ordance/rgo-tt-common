package rgo.tt.common.persistence.sqlstatement.retry;

import java.util.Objects;

public final class SqlRetryParameters {

    private int attempts;

    public SqlRetryParameters() {
    }

    public SqlRetryParameters(int attempts) {
        this.attempts = attempts;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlRetryParameters that = (SqlRetryParameters) o;
        return attempts == that.attempts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempts);
    }

    @Override
    public String toString() {
        return "SqlRetryParameters{" +
                "attempts=" + attempts +
                '}';
    }
}
