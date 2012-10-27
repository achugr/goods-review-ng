package ru.goodsreview.frontend;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.apache.log4j.pattern.LogEvent;
import org.json.JSONObject;
import org.junit.*;

import java.io.IOException;

/**
 * @author Artemii Chugreev achugr@yandex-team.ru
 *         19.10.12
 */
public class SimpleLoadTest {
    private static final Logger log = Logger.getLogger(SimpleLoadTest.class);


//    TODO i am sure, that this test is useless and don't show any useful information about server performance under high load :)
//    TODO in nearest future we will use JMeter for performance testing
//    TODO but this test allows evaluate average time of page loading(without browser-rendering)
    @org.junit.Test
    public void loadTest() throws IOException {
        final int requestNumber = 20;
        final long start = System.currentTimeMillis();
//        TODO we mustn't do this in one thread, i think =)
        for (int i = 0; i < requestNumber; i++) {
            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod("http://localhost:8080");
            httpClient.executeMethod(getMethod);
            Assert.assertEquals(getMethod.getStatusCode(), 200);
        }
        final long finish = System.currentTimeMillis();
        final long timeForOneResponse = (finish-start)/requestNumber;
        log.info(timeForOneResponse + " millis for one response");
    }
}
