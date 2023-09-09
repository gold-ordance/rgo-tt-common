package rgo.tt.common.persistence.sqlstatement.retry;

public interface RetryableOperation<T> {

    T get();
}
