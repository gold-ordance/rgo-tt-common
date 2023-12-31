package rgo.tt.common.persistence.sqlstatement.retry;

import org.apache.commons.lang3.exception.ExceptionUtils;
import rgo.tt.common.exceptions.PersistenceException;

import java.util.HashSet;
import java.util.Set;

public final class DbRetryManager {

    private final RetryPolicyProperties policy;

    public DbRetryManager(RetryPolicyProperties policy) {
        this.policy = policy;
    }

    public <T> T execute(RetryableOperation<T> operation, OperationParameters parameters) {
        SqlRetryParameters params = policy.retryParams(parameters);
        Set<String> exceptions = new HashSet<>();
        int attempts = 0;

        while (attempts != params.getAttempts()) {
            try {
                return operation.perform();
            } catch (Exception e) {
                if (e.getClass().isAssignableFrom(params.getException())) {
                    exceptions.add(ExceptionUtils.getStackTrace(e));
                    attempts++;
                    continue;
                }
                throw e;
            }
        }

        throw new PersistenceException("Failed to execute query after " + attempts + " attempts. Reason: " + exceptions);
    }
}
