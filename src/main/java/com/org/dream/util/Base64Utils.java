package com.org.dream.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * The type Base 64 utils.
 */
public class Base64Utils {
    /**
     * Base 64 encoder string.
     * @param key the key
     * @return the string
     */
    public static String base64Encoder(String key) {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(key.getBytes());
        return encode;
    }

    /**
     * Base 64 decoder string.
     * @param encode the encode
     * @return the string
     */
    public static String base64Decoder(String encode) {
        BASE64Decoder decoder = new BASE64Decoder();
        String str = null;
        try {
            byte[] bytes = decoder.decodeBuffer(encode);
            str = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
