package rgo.tt.common.persistence.sqlstatement.retry;

public class OperationParameters {

    private final Class<?> entity;
    private final String methodName;

    private OperationParameters(Class<?> entity, String methodName) {
        this.entity = entity;
        this.methodName = methodName;
    }

    public static OperationParameters from(Class<?> entity, String methodName) {
        return new OperationParameters(entity, methodName);
    }

    public Class<?> getEntity() {
        return entity;
    }

    public String getMethodName() {
        return methodName;
    }
}
