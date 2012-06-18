package ru.goodsreview.core.util;

import java.math.BigInteger;
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

    public static String hash(byte[] data) {
        DIGEST.reset();
        DIGEST.update(data);
        BigInteger hash = new BigInteger(1, DIGEST.digest());
        return hash.toString(16);
    }

    public static String hash(String data) {
        return hash(data.getBytes());
    }
}
