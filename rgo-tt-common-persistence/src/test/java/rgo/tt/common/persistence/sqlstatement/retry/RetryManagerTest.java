package rgo.tt.common.persistence.sqlstatement.retry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rgo.tt.common.exceptions.PersistenceException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetryManagerTest {

    private RetryManager manager;
    @Mock private RetryPolicyProperties properties;

    @BeforeEach
    void setUp() {
        manager = new RetryManager(properties);
    }

    @Test
    void execute_attemptsAreExhausted() {
        SqlRetryParameters params = new SqlRetryParameters();
        params.setAttempts(3);
        params.setException(ExpectedException.class);

        RetryableOperation<?> operation = () -> {
            throw new ExpectedException();
        };

        when(properties.policy(any())).thenReturn(params);

        assertThrows(PersistenceException.class, () -> manager.execute(operation, any()));
    }

    @Test
    void execute_unexpectedException_skipRetry() {
        SqlRetryParameters params = new SqlRetryParameters();
        params.setAttempts(3);
        params.setException(ExpectedException.class);

        RetryableOperation<?> operation = () -> {
            throw new UnexpectedException();
        };

        when(properties.policy(any())).thenReturn(params);

        assertThrows(UnexpectedException.class, () -> manager.execute(operation, any()));
    }

    @Test
    void execute_success() {
        SqlRetryParameters params = new SqlRetryParameters();
        params.setAttempts(3);

        RetryableOperation<?> operation = () -> String.class;

        when(properties.policy(any())).thenReturn(params);

        assertDoesNotThrow(() -> manager.execute(operation, any()));
    }

    private static class ExpectedException extends RuntimeException {
    }

    private static class UnexpectedException extends RuntimeException {
    }
}
