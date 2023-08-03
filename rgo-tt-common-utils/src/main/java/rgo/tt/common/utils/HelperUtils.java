package rgo.tt.common.utils;

public final class HelperUtils {

    private HelperUtils() {
    }

    public static String size(String str) {
        if (str == null) return null;
        return "{size=" + str.length() + "}";
    }
}
