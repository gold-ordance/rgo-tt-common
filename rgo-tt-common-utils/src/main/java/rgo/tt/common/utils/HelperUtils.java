package rgo.tt.common.utils;

public final class HelperUtils {

    private static final int FIRST_SYMBOL_INDEX = 0;

    private HelperUtils() {
    }

    public static String size(String str) {
        if (str == null) return null;
        return "{size=" + str.length() + "}";
    }

    public static String getFirstSymbol(String str) {
        if (str == null) return null;
        if (str.isEmpty()) return "";
        return String.valueOf(
                str.charAt(FIRST_SYMBOL_INDEX));
    }
}
