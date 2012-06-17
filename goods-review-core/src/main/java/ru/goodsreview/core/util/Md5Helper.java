package ru.goodsreview.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 22:38
 */
public class Md5Helper {

    private final static MessageDigest DIGEST;

    static {
        try {
            DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] hash(byte[] data) {
        DIGEST.reset();
        DIGEST.update(data);
        return DIGEST.digest();
    }
}
