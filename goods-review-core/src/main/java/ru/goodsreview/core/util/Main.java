package ru.goodsreview.core.util;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * User: daddy-bear
 * Date: 18.06.12
 * Time: 0:05
 */
public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(final String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("invalid arguments number");
        }
        new FileSystemXmlApplicationContext(new String[]{args[0]});
        log.info("app started");
    }
}
