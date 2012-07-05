package ru.goodsreview.util;

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
public abstract class StringUtil {

    private StringUtil(){}

    public static String inputStreamToString(InputStream inputStream){

    	//read it with BufferedReader
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return sb.toString();
    }


}
