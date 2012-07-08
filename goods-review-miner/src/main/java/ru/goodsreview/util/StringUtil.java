package ru.goodsreview.util;

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

    private StringUtil(){}

    public static String inputStreamToString(InputStream inputStream){

//    	read it with BufferedReader
    	BufferedReader br
        	= new BufferedReader(
        		new InputStreamReader(inputStream));

    	StringBuilder sb = new StringBuilder();

    	String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
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
