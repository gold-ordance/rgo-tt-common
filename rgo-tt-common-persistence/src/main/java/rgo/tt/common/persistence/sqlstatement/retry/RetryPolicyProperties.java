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

    SqlRetryParameters retryParams(OperationParameters params) {
        String entityName = entityName(params.getEntity());
        Map<String, SqlRetryParameters> methods = entities.get(entityName);
        return methods.get(params.getMethodName());
    }

    private String entityName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase(Locale.ENGLISH);
    }

    public Map<String, Map<String, SqlRetryParameters>> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, Map<String, SqlRetryParameters>> entities) {
        this.entities = entities;
    }
}
