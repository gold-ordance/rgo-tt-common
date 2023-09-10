package rgo.tt.common.persistence.sqlstatement.retry;

public class SqlRetryParameters {

    private int attempts;
    private Class<?> exception;

    public SqlRetryParameters() {
    }

    private SqlRetryParameters(Builder builder) {
        attempts = builder.attempts;
        exception = builder.exception;
        checkValidity();
    }

    void checkValidity() {
        if (attempts < 1) {
            throw new IllegalArgumentException("The number of attempts must be positive. actual=" + attempts);
        }

        if (exception == null) {
            throw new IllegalArgumentException("The exception must be not null.");
        }
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Class<?> getException() {
        return exception;
    }

    public void setException(Class<?> exception) {
        this.exception = exception;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int attempts;
        private Class<?> exception;

        public Builder setAttempts(int attempts) {
            this.attempts = attempts;
            return this;
        }

        public Builder setException(Class<?> exception) {
            this.exception = exception;
            return this;
        }

        public SqlRetryParameters build() {
            return new SqlRetryParameters(this);
        }
    }
}
