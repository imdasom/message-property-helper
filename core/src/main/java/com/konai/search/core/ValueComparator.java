package com.konai.search.core;

public class ValueComparator {

    public static boolean compareTotalToken(String[] baseTokens, String[] comparedTokens) {
        if(baseTokens.length != comparedTokens.length) {
            return false;
        }
        for (int i = 0; i < baseTokens.length; i++) {
            if(!baseTokens[i].equals(comparedTokens[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean comparePartialToken(String[] partialTokens, String[] comparedTokens) {
        int j = 0;
        for (int i = 0; i < comparedTokens.length; i++) {
            if(comparedTokens[i].equals(partialTokens[j])) {
                j++;
                if(j == partialTokens.length) {
                    return true;
                }
            } else {
                j = 0;
            }
        }
        return false;
    }

    public static boolean compareValue(String str, String pattern) {
        int[] pi = getPi(pattern);
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            while(j > 0 && (str.charAt(i) != pattern.charAt(j))) {
                j = pi[j - 1];
            }
            if(str.charAt(i) == pattern.charAt(j)) {
                if(j == pattern.length() - 1) {
                    return true;
                } else {
                    j++;
                }
            }
        }
        return false;
    }

    private static int[] getPi(String pattern) {
        int patternSize = pattern.length();
        int j = 0;
        int[] pi = new int[patternSize];
        for(int i=1; i<patternSize; i++) {
            while(j > 0 && (pattern.charAt(i) != pattern.charAt(j))) {
                j = pi[j - 1];
            }
            if(pattern.charAt(i) == pattern.charAt(j)) {
                pi[i] = ++j;
            }
        }
        return pi;
    }
}
