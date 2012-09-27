package ru.goodsreview.core.util;

import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.Test;

import static ru.goodsreview.core.util.Md5Helper.hash;
/**
 * User: daddy-bear
 * Date: 18.06.12
 * Time: 18:24
 */
public class Md5HelperTest {

    @Test
    public void testMd5Hash() {

        final String hash = hash("абвгд".getBytes());
        System.out.println(hash);
        Assert.assertEquals(hash, "1a21452a078f2a09d40b1d69c58beb8c");

        final String hash1 = hash("абвгд".getBytes());
        System.out.println(hash1);
        Assert.assertEquals(hash1, "1a21452a078f2a09d40b1d69c58beb8c");

        final String hash2 = hash("йцукен".getBytes());
        System.out.println(hash2);
        Assert.assertEquals(hash2, "acdc9128f7b0f3a84ed5b0a1640f17c7");
    }

    @Test
    public void testJsonObjectMd5() throws Exception {

        final JSONObject jsonObject = new JSONObject("{\"a\":1, b:[\"1\", {\"c\": \"s\"}]}");
        System.out.println(jsonObject);
        System.out.println(hash(jsonObject));

        final JSONObject jsonObject2 = new JSONObject("{b:[{\"c\": \"s\"}, \"1\"], \"a\":1}");
        System.out.println(jsonObject2);
        System.out.println(hash(jsonObject2));

        Assert.assertEquals(hash(jsonObject2), hash(jsonObject));

    }
}
