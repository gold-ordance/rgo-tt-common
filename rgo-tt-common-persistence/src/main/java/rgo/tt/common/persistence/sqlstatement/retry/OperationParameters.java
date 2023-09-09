package rgo.tt.common.persistence.sqlstatement.retry;

public class OperationParameters {

    private final Class<?> entity;
    private final String methodName;
    private final Class<?> expectedException;

    private OperationParameters(Class<?> entity, String methodName, Class<?> expectedException) {
        this.entity = entity;
        this.methodName = methodName;
        this.expectedException = expectedException;
    }

    public static OperationParameters from(Class<?> entity, String methodName, Class<?> expectedException) {
        return new OperationParameters(entity, methodName, expectedException);
    }

    public Class<?> getEntity() {
        return entity;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class getExpectedException() {
        return expectedException;
    }
}
