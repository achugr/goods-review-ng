package ru.goodsreview.core.util;

import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;

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

    public static String hash(final byte[] data) {
        final BigInteger hash = new BigInteger(1, DIGEST.digest(data));
        return hash.toString(16);
    }

    public static String hash(final String data) {
        return hash(data.getBytes());
    }

    public static String hash(final JSONObject jsonObject) {
        return jsonObject.
    }

    private static final Comparator<Pair<String, String>> NAME_VALUE_PAIR_COMPARATOR = new Comparator<Pair<String, String>>() {
        @Override
        public int compare(final Pair<String, String> p1, final Pair<String, String> p2) {
            int result = p1.first.compareTo(p2.first);
            result = (result == 0) ? result : p1.second.compareTo(p2.second);
            return result;
        }
    }
}
