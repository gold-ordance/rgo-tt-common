package rgo.tt.common.persistence.sqlstatement.retry;

import java.util.Locale;
import java.util.Map;

public class RetryPolicyProperties {

    private Map<String, Map<String, SqlRetryParameters>> entities;

    public RetryPolicyProperties() {
    }

    public RetryPolicyProperties(Map<String, Map<String, SqlRetryParameters>> entities) {
        this.entities = entities;
    }

    public SqlRetryParameters policy(Class<?> clazz, String method) {
        String entity = clazz.getSimpleName().toLowerCase(Locale.ENGLISH);
        Map<String, SqlRetryParameters> methods = entities.get(entity);
        return methods.get(method);
    }

    public Map<String, Map<String, SqlRetryParameters>> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, Map<String, SqlRetryParameters>> entities) {
        this.entities = entities;
    }
}
