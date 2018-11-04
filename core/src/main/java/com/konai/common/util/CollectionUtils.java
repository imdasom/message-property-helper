package com.konai.common.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    public static <T> List<T> mergeList(List<T> list1, List<T> list2) {
        List<T> mergedList = new ArrayList<>();
        mergedList.addAll(list1);
        mergedList.addAll(list2);
        return mergedList;
    }
}
