package rgo.tt.common.persistence.sqlresult;

import java.util.Objects;

public final class SqlDeleteResult {

    private final boolean isDeleted;

    private SqlDeleteResult(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public static SqlDeleteResult from(boolean isDeleted) {
        return new SqlDeleteResult(isDeleted);
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlDeleteResult that = (SqlDeleteResult) o;
        return isDeleted == that.isDeleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isDeleted);
    }

    @Override
    public String toString() {
        return "SqlDeleteResult{" +
                "isDeleted=" + isDeleted +
                '}';
    }
}
