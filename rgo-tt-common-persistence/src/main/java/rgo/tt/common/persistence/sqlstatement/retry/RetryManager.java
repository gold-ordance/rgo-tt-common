package rgo.tt.common.persistence.sqlstatement.retry;

import rgo.tt.common.exceptions.PersistenceException;

import java.util.HashSet;
import java.util.Set;

public final class RetryManager {

    private final RetryPolicyProperties policy;

    public RetryManager(RetryPolicyProperties policy) {
        this.policy = policy;
    }

    public <T> T execute(RetryableOperation<T> function, OperationParameters parameters) {
        SqlRetryParameters params = policy.policy(parameters);
        Set<Exception> exceptions = new HashSet<>();
        int attempts = 0;

        while (attempts != params.getAttempts()) {
            try {
                return function.get();
            } catch (Exception e) {
                if (e.getClass().isAssignableFrom(params.getException())) {
                    exceptions.add(e);
                    attempts++;
                    continue;
                }
                throw e;
            }
        }

        throw new PersistenceException("Failed to execute query after " + attempts + " attempts. Reason: " + exceptions);
    }
}
