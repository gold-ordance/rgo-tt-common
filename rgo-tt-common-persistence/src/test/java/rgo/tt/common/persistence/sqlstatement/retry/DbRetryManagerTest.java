package rgo.tt.common.persistence.sqlstatement.retry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rgo.tt.common.exceptions.PersistenceException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DbRetryManagerTest {

    private DbRetryManager manager;
    @Mock private RetryPolicyProperties properties;

    @BeforeEach
    void setUp() {
        manager = new DbRetryManager(properties);
    }

    @Test
    void execute_attemptsAreExhausted() {
        SqlRetryParameters params = new SqlRetryParameters();
        params.setAttempts(3);
        params.setException(ExpectedException.class);

        RetryableOperation<?> operation = () -> {
            throw new ExpectedException();
        };

        when(properties.retryParams(any())).thenReturn(params);

        assertThatThrownBy(() -> manager.execute(operation, any()))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    void execute_unexpectedException_skipRetry() {
        SqlRetryParameters params = new SqlRetryParameters();
        params.setAttempts(3);
        params.setException(ExpectedException.class);

        RetryableOperation<?> operation = () -> {
            throw new UnexpectedException();
        };

        when(properties.retryParams(any())).thenReturn(params);

        assertThatThrownBy(() -> manager.execute(operation, any()))
                .isInstanceOf(UnexpectedException.class);
    }

    @Test
    void execute_success() {
        SqlRetryParameters params = new SqlRetryParameters();
        params.setAttempts(3);

        RetryableOperation<?> operation = () -> String.class;

        when(properties.retryParams(any())).thenReturn(params);

        assertThatCode(() -> manager.execute(operation, any()))
                .doesNotThrowAnyException();
    }

    private static class ExpectedException extends RuntimeException {
    }

    private static class UnexpectedException extends RuntimeException {
    }
}
