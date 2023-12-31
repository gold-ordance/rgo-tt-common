package rgo.tt.common.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DbTxManagerTest {

    private DbTxManager txManager;
    @Mock private DataSource dataSource;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = mock(Connection.class);
        lenient().when(dataSource.getConnection()).thenReturn(connection);
        txManager = new DbTxManager(dataSource);
    }

    @Test
    void tx_success() throws SQLException {
        Connection connection = txManager.tx(action);

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).commit();
        verify(connection, times(0)).rollback();
        verify(connection, times(1)).close();
    }

    @Test
    void tx_exception() throws SQLException {
        AtomicReference<Connection> connection = new AtomicReference<>();

        try {
            txManager.tx(() -> {
                connection.set(action.get());
                throw new ExpectedException();
            });
            fail();
        } catch (Exception e) {
            assertThat(e.getCause()).isInstanceOf(ExpectedException.class);
        }

        assertThat(connection.get()).isNotNull();
        verify(connection.get(), times(0)).commit();
        verify(connection.get(), times(1)).rollback();
        verify(connection.get(), times(1)).close();
    }

    @Test
    void enclosedTx_success() throws SQLException {
        AtomicReference<Connection> innerConnection = new AtomicReference<>();

        Connection outerConnection = txManager.tx(() -> {
            innerConnection.set(txManager.tx(action));
            return action.get();
        });

        assertThat(innerConnection.get()).isEqualTo(outerConnection);
        verify(outerConnection, times(1)).commit();
        verify(outerConnection, times(0)).rollback();
        verify(outerConnection, times(1)).close();
    }

    @Test
    void enclosedTx_exception() throws SQLException {
        AtomicReference<Connection> innerConnection = new AtomicReference<>();

        try {
            txManager.tx(() -> {
                innerConnection.set(action.get());
                txManager.tx(() -> {
                    throw new ExpectedException();
                });
                fail();
            });
            fail();
        } catch (Exception e) {
            assertThat(e.getCause()).isInstanceOf(ExpectedException.class);
        }

        assertThat(innerConnection.get()).isNotNull();
        verify(innerConnection.get(), times(0)).commit();
        verify(innerConnection.get(), times(1)).rollback();
        verify(innerConnection.get(), times(1)).close();
    }

    private final Supplier<Connection> action = () -> {
      try {
          return txManager.getConnection();
      } catch (Exception e) {
          throw new UnexpectedException(e);
      }
    };

    private static final class ExpectedException extends RuntimeException {
    }

    private static final class UnexpectedException extends RuntimeException {

        public UnexpectedException(Throwable cause) {
            super(cause);
        }
    }
}
