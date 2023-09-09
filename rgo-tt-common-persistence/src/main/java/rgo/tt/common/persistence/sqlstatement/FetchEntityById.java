package rgo.tt.common.persistence.sqlstatement;

public interface FetchEntityById<T> {

    T fetch(long entityId);
}
