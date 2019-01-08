package com.konai.common.util;

public class StringUtils {

    public static boolean isEmpty(String value) {
        if(value == null) {
            return true;
        }
        if("".equals(value)) {
            return true;
        }
        return false;
    }

    public static String getZeroPaddingNumber(int num, int base) {
        int count = (int) Math.log10(num);
        String paddingNum = "";
        for (int i = 1; i < base - count; i++) {
            paddingNum += "0";
        }
        return paddingNum + num;
    }

    public static String getString(String value, String defaultValue) {
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }
}
