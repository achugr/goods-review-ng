package ru.goodsreview.core.util;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static ru.goodsreview.core.util.Md5Helper.hash;
/**
 * User: daddy-bear
 * Date: 18.06.12
 * Time: 18:24
 */
@Ignore
public class Md5HelperTest {

    @Test
    public void testMd5Hash() {

        final String hash = hash("абвгд".getBytes());
        System.out.println(hash);
        Assert.assertEquals(hash, "9cdb481887ae90c61ad975dc8e2eaeff");

        final String hash1 = hash("абвгд".getBytes());
        System.out.println(hash1);
        Assert.assertEquals(hash1, "9cdb481887ae90c61ad975dc8e2eaeff");

        final String hash2 = hash("йцукен".getBytes());
        System.out.println(hash2);
        Assert.assertEquals(hash2, "41890cd2ac71e06b5f2c9ad5ccc07b45");
    }
}
