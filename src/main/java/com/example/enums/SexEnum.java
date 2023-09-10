package com.example.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum SexEnum {
    /**
     * 男性
     */
    MAN,
    /**
     * 女性
     */
    WOMAN;

    /**
     * 判断所传数值是否包含于枚举当中
     *
     * @param sex
     * @return
     */
    public static boolean isOutRangeSex(Integer sex) {
        List<Integer> sexEnumList = toList().stream().map(Enum::ordinal).collect(Collectors.toList());

        if (sexEnumList.contains(sex)) {
            return true;
        }

        return false;
    }

    /**
     * 获得所有枚举类型到list
     * @return
     */
    public static List<SexEnum> toList() {
        List<SexEnum> list = new ArrayList<>();
        SexEnum[] values = values();
        Collections.addAll(list, values);

        return list;
    }
}
