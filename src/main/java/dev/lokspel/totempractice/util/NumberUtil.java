package dev.lokspel.totempractice.util;

public final class NumberUtil {

    private NumberUtil() {
    }

    public static String format(double value) {
        if (value == Math.floor(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}