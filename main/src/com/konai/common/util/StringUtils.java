package com.konai.common.util;

public class StringUtils {

    public static boolean isEmpty(String value) {
        if(value == null) {
            return false;
        }
        if("".equals(value)) {
            return false;
        }
        return true;
    }

    public static String getZeroPaddingNumber(int num, int base) {
        int count = (int) Math.log10(num);
        String paddingNum = "";
        for (int i = 1; i < base - count; i++) {
            paddingNum += "0";
        }
        return paddingNum + num;
    }
}
