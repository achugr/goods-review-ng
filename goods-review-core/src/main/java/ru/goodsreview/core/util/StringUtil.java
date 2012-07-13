package ru.goodsreview.core.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Artemij Chugreev
 * Date: 17.06.12
 * Time: 19:10
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
public final class StringUtil {

    private final static Logger log = Logger.getLogger(StringUtil.class);

    private StringUtil() {
    }

    public static String inputStreamToString(final InputStream inputStream) {

        final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder sb = new StringBuilder();

        try {
            while (br.ready()) {
                sb.append(br.readLine());
            }
        } catch (IOException e) {
            log.error("can't read line from buffered reader", e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                log.error("can't close buffered reader", e);
            }
        }

        return sb.toString();
    }


}
