package com.org.dream.util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Rsa util.
 * @Desc: RSA加解密与签名验签  1、公钥加密，则用私钥解密 2、私钥加密，则用公钥解密 3、公钥签名，私钥验签 4、加密类型：非对称加密 优缺点： 优点：更安全，秘钥越长，越难破解 缺点：加密速度慢
 */
public class RSAUtil {

    /**
     * The constant CHARSET.
     */
    public static final String CHARSET = "UTF-8";
    /**
     * The constant RSA_ALGORITHM.
     */
    public static final String RSA_ALGORITHM = "RSA";
    /**
     * The constant RSA_ALGORITHM_SIGN.
     */
    public static final String RSA_ALGORITHM_SIGN = "SHA256WithRSA";

    /**
     * 初始化RSA算法密钥对
     * @param keySize RSA1024已经不安全了,建议2048
     * @return 经过Base64编码后的公私钥Map, 键名分别为publicKey和privateKey
     */
    private static Map<String, String> createKeys(int keySize) {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        return keyPairMap;
    }

    /**
     * 得到公钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @return the public key
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws InvalidKeySpecException  the invalid key spec exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @return the private key
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws InvalidKeySpecException  the invalid key spec exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     * @param data      the data
     * @param publicKey the public key
     * @param encoding  the encoding
     * @return string
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = CHARSET;
        }
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(encoding), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     * @param data       the data
     * @param privateKey the private key
     * @param encoding   the encoding
     * @return string
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = CHARSET;
        }
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), encoding);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     * @param data       the data
     * @param privateKey the private key
     * @param encoding   the encoding
     * @return string
     */
    public static String privateEncrypt(String data, RSAPrivateKey privateKey, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = CHARSET;
        }
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(encoding), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     * @param data      the data
     * @param publicKey the public key
     * @param encoding  the encoding
     * @return string
     */
    public static String publicDecrypt(String data, RSAPublicKey publicKey, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = CHARSET;
        }
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), encoding);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥签名
     * @param data       需要签名的数据
     * @param privateKey 私钥
     * @param encoding   the encoding
     * @return string
     */
    public static String sign(String data, RSAPrivateKey privateKey, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = CHARSET;
        }
        try {
            //sign
            Signature signature = Signature.getInstance(RSA_ALGORITHM_SIGN);
            signature.initSign(privateKey);
            signature.update(data.getBytes(encoding));
            return Base64.encodeBase64URLSafeString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("签名字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥验签
     * @param data      需要验签的原始数据
     * @param sign      签名
     * @param publicKey 公钥
     * @param encoding  the encoding
     * @return boolean
     */
    public static boolean verify(String data, String sign, RSAPublicKey publicKey, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = CHARSET;
        }
        try {
            Signature signature = Signature.getInstance(RSA_ALGORITHM_SIGN);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(encoding));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            throw new RuntimeException("验签字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

    /**
     * The entry point of application.
     * @param args the input arguments
     * @throws InvalidKeySpecException  the invalid key spec exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Map<String, String> secretMap = RSAUtil.createKeys(2048);
        RSAPublicKey publicKey = RSAUtil.getPublicKey(secretMap.get("publicKey"));
        RSAPrivateKey privateKey = RSAUtil.getPrivateKey(secretMap.get("privateKey"));
        test(publicKey, privateKey);
        System.out.println("==============================================");
        test1(publicKey, privateKey);
    }

    /**
     * Test 1.
     * @param publicKey  the public key
     * @param privateKey the private key
     */
    public static void test1(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        String contentStr = "jack-barry";
        // 私钥加密
        String encryptStr = RSAUtil.privateEncrypt(contentStr, privateKey, StandardCharsets.UTF_8.name());
        System.out.println("私钥加密:" + encryptStr);
        // 公钥解密
        System.out.println("公钥解密:" + RSAUtil.publicDecrypt(encryptStr, publicKey, StandardCharsets.UTF_8.name()));
    }

    /**
     * Test.
     * @param publicKey  the public key
     * @param privateKey the private key
     */
    public static void test(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        String content = "dream-barry";
        // 公钥加密
        String encrypt = RSAUtil.publicEncrypt(content, publicKey, StandardCharsets.UTF_8.name());
        System.out.println("公钥加密:" + encrypt);
        // 私钥解密
        System.out.println("私钥解密:" + RSAUtil.privateDecrypt(encrypt, privateKey, StandardCharsets.UTF_8.name()));
    }

}