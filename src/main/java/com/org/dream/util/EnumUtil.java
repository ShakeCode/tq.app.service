package com.org.dream.util;


import com.org.dream.enums.BaseEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class EnumUtil {

    private static final Map<Class<? extends Enum<?>>, Map<Integer, ? extends Enum<? extends BaseEnum>>> CLASS_ENUM_MAP =
            new ConcurrentHashMap<>(16);

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E> & BaseEnum> E match(Class<E> enumClass, Integer type) {
        Map enumMap = CLASS_ENUM_MAP.get(enumClass);
        if (Objects.isNull(enumMap)) {
            Map unmodifiableMap = Arrays.stream(enumClass.getEnumConstants())
                    .collect(Collectors.toConcurrentMap(BaseEnum::getCode, v -> v));
            CLASS_ENUM_MAP.putIfAbsent(enumClass, unmodifiableMap);
            return (E) unmodifiableMap.get(type);
        }
        return (E) enumMap.get(type);
    }

    /**
     * 根据code获取枚举
     * @param enumClass
     * @param code
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & BaseEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据msg获取枚举
     * @param enumClass
     * @param msg
     * @param <E>
     * @return
     */
    public static <E extends Enum<?> & BaseEnum> E msgOf(Class<E> enumClass, String msg) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getDescription().equals(msg)) {
                return e;
            }
        }
        return null;
    }



}
