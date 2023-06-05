package com.org.dream.util;

import java.util.UUID;


/**
 * The type Key generator util.
 */
public class KeyGeneratorUtil {

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getKey(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }
}
