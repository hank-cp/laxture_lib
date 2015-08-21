package com.laxture.lib.util;

import java.text.DecimalFormat;

public class NumberUtil {

    public static String formatInt(int num, String pattern) {
        return new DecimalFormat(pattern).format(num);
    }

    public static String formatLong(long num, String pattern) {
        return new DecimalFormat(pattern).format(num);
    }

    public static int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception ignored) {}
        return 0;
    }

}
