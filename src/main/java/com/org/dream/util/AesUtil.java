package com.org.dream.util;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * The type Aes util.
 * <p>
 * https://blog.csdn.net/thlzjfefe/article/details/123465352
 */
public class AesUtil {
    /**
     * The constant CHARSET.
     */
    public static final String CHARSET = "UTF-8";
    /**
     * The constant AES_ALGORITHM.
     */
    public static final String AES_ALGORITHM = "AES";

    /**
     * Encrypt 4 aes string.
     * @param content   加密前数据
     * @param secretKey the secret key
     * @param encoding  the encoding
     * @return string string
     * @description: AES加密算法入口
     */
    public static String encrypt4Aes(String content, String secretKey, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = CHARSET;
        }
        try {
            byte[] src = content.getBytes(encoding);
            //加密
            byte[] bytOut = encryptMode(src, secretKey);
            return base64encode(bytOut);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("加密时string转byte出错：", e);
        }
    }

    /**
     * Encrypt mode byte [ ].
     * @param src       加密前数据字节
     * @param secretKey the secret key
     * @return byte [ ]
     * @description: AES加密实现
     */
    public static byte[] encryptMode(byte[] src, String secretKey) {
        try {
            Cipher cip = Cipher.getInstance(AES_ALGORITHM);
            cip.init(Cipher.ENCRYPT_MODE, getSecretKey(secretKey));
            return cip.doFinal(src);
        } catch (Exception e3) {
            throw new RuntimeException("加密出现异常：", e3);
        }
    }

    /**
     * Base 64 encode string.
     * @param src the src
     * @return the string
     */
    public static String base64encode(byte[] src) {
        if (src == null) return null;
        return (new sun.misc.BASE64Encoder()).encode(src);
    }

    /**
     * Gets secret key.
     * @param secretKey the secret key
     * @return the secret key
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws NoSuchPaddingException   the no such padding exception
     * @throws InvalidKeyException      the invalid key exception
     */
    public static SecretKey getSecretKey(String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        byte[] keybyte = getKeyByStr(secretKey);
        // 初始化算法,设置成“SHA1PRNG”是为了防止在linux环境下随机生成算法
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(keybyte);
        KeyGenerator keygen = KeyGenerator.getInstance(AES_ALGORITHM);
        keygen.init(secureRandom);
        return keygen.generateKey();
    }

    /**
     * Get key by str byte [ ].
     * @param str the str
     * @return the byte [ ]
     */
    public static byte[] getKeyByStr(String str) {
        byte[] bRet = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            Integer itg = new Integer(16 * getChrInt(str.charAt(2 * i)) + getChrInt(str.charAt(2 * i + 1)));
            bRet[i] = itg.byteValue();
        }
        return bRet;
    }

    /**
     * Gets chr int.
     * @param chr the chr
     * @return the chr int
     */
    public static int getChrInt(char chr) {
        int iRet = 0;
        if (chr == "0".charAt(0)) iRet = 0;
        if (chr == "1".charAt(0)) iRet = 1;
        if (chr == "2".charAt(0)) iRet = 2;
        if (chr == "3".charAt(0)) iRet = 3;
        if (chr == "4".charAt(0)) iRet = 4;
        if (chr == "5".charAt(0)) iRet = 5;
        if (chr == "6".charAt(0)) iRet = 6;
        if (chr == "7".charAt(0)) iRet = 7;
        if (chr == "8".charAt(0)) iRet = 8;
        if (chr == "9".charAt(0)) iRet = 9;
        if (chr == "A".charAt(0)) iRet = 10;
        if (chr == "B".charAt(0)) iRet = 11;
        if (chr == "C".charAt(0)) iRet = 12;
        if (chr == "D".charAt(0)) iRet = 13;
        if (chr == "E".charAt(0)) iRet = 14;
        if (chr == "F".charAt(0)) iRet = 15;
        return iRet;
    }

    /**
     * AES算法解密入口
     * @param contentbase64 the contentbase 64
     * @param secretKey     the secret key
     * @param encoding      the encoding
     * @return the string
     */
    public static String decrypt4Aes2Str(String contentbase64, String secretKey, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = CHARSET;
        }
        String Result = null;
        byte[] dst = decrypt4Aes(contentbase64, secretKey);
        if (null != dst) {
            try {
                Result = new String(dst, encoding);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("解密时byte转string出现异常：", e);
            }
        }
        return Result;
    }

    /**
     * Decrypt 4 aes byte [ ].
     * @param contentbase64 the contentbase 64
     * @param secretKey     the secret key
     * @return the byte [ ]
     */
    public static byte[] decrypt4Aes(String contentbase64, String secretKey) {
        byte[] src = base64decode(contentbase64);
        // 解密
        return decryptMode(src, secretKey);
    }

    /**
     * Decrypt mode byte [ ].
     * @param src       the src
     * @param secretKey the secret key
     * @return the byte [ ]
     */
    public static byte[] decryptMode(byte[] src, String secretKey) {
        try {
            Cipher cip = Cipher.getInstance(AES_ALGORITHM);
            cip.init(Cipher.DECRYPT_MODE, getSecretKey(secretKey));
            return cip.doFinal(src);
        } catch (Exception e) {
            throw new RuntimeException("解密时出现异常：", e);
        }
    }

    /**
     * Base 64 decode byte [ ].将 BASE64 编码的字符串 s 进行解码
     * @param s the s
     * @return the byte [ ]
     */
    public static byte[] base64decode(String s) {
        if (s == null)
            return null;
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return b;
        } catch (Exception e) {
            throw new RuntimeException("base64编解码出现异常：", e);
        }
    }

    /**
     * The entry point of application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // 密钥
        String secretKey = MD5Util.md5("dswdfrervvbhghfhhxcxASS");
        // 加密内容
        String content = "dreamli";
        String encryptStr = encrypt4Aes(content, secretKey, StandardCharsets.UTF_8.name());
        System.out.println("aes 对称加密:" + encryptStr);
        System.out.println("aes 对称解密:" + new String(decrypt4Aes(encryptStr, secretKey), StandardCharsets.UTF_8));
    }
}
