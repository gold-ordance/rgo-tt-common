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
    @Mock private OperationParameters parameters;

    @BeforeEach
    void setUp() {
        manager = new RetryManager(properties);
    }

    @Test
    void execute_attemptsAreExhausted() {
        int attempts = 3;
        SqlRetryParameters params = new SqlRetryParameters(attempts);
        RetryableOperation<?> operation = () -> {
            throw new ExpectedException();
        };

        when(properties.policy(any())).thenReturn(params);
        when(parameters.getExpectedException()).thenReturn(ExpectedException.class);

        assertThrows(PersistenceException.class, () -> manager.execute(operation, parameters));
    }

    @Test
    void execute_unexpectedException_skipRetry() {
        int attempts = 3;
        SqlRetryParameters params = new SqlRetryParameters(attempts);
        RetryableOperation<?> operation = () -> {
            throw new UnexpectedException();
        };

        when(properties.policy(any())).thenReturn(params);
        when(parameters.getExpectedException()).thenReturn(ExpectedException.class);

        assertThrows(UnexpectedException.class, () -> manager.execute(operation, parameters));
    }

    @Test
    void execute_success() {
        int attempts = 3;
        SqlRetryParameters params = new SqlRetryParameters(attempts);
        RetryableOperation<?> operation = () -> String.class;

        when(properties.policy(any())).thenReturn(params);

        assertDoesNotThrow(() -> manager.execute(operation, any()));
    }

    private static class ExpectedException extends RuntimeException {
    }

    private static class UnexpectedException extends RuntimeException {
    }
}
