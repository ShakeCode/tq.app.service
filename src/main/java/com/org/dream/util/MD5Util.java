package com.org.dream.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * The type Md 5 util. 哈希散列加密,不可逆 （可用于号码唯一值校验加密入库）
 */
@Slf4j
public class MD5Util {
    /**
     * The entry point of application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        String md5 = md5("fdsfftsetetrw");
        System.out.println("md5加密：" + md5);
    }

    // 用来将字节转换成 16 进制表示的字符
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static Charset UTF8 = Charset.forName("UTF-8");

    private MD5Util() {
        throw new RuntimeException("can't init it");
    }

    /**
     * Md 5 string.
     * @param from the from
     * @return the string
     */
    public static String md5(String from) {
        byte fromByte[] = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            fromByte = from.getBytes(UTF8);
        } catch (Throwable e) {
            log.info("error while md5 for:" + from, e);
            throw new RuntimeException("error while md5 for:" + from, e);
        }
        byte bs[] = md.digest(fromByte);
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = bs[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        return new String(str);
    }
}