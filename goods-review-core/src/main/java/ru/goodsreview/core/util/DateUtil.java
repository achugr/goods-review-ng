package ru.goodsreview.core.util;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: daddy-bear
 * Date: 14.07.12
 * Time: 14:56
 */
public final class DateUtil {
    private final static Logger log = Logger.getLogger(DateUtil.class);

    private DateUtil() {
    }

    private static final DateFormat BASE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public static Date parseFromBaseFormat(final String date) {
        try {
            return BASE_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            log.error("date in invalid format - " + date, e);
            throw new RuntimeException(e);
        }
    }
}
