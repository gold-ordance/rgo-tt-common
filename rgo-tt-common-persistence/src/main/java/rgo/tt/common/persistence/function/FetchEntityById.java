package rgo.tt.common.persistence.function;

public interface FetchEntityById<T> {

    T fetch(long entityId);
}
